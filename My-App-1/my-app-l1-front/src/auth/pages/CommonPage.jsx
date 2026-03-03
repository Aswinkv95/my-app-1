import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import SideMenu from "../components/SideMenu";
import Header from "../components/Header";
import Footer from "../components/Footer";
import VisaException from "../../Reports/components/VisaException";
import FileUpload from "../../Reports/components/FileUpload";
// Map section names to components
const sectionComponents = {
  "visa-exception": VisaException,
  "file-upload": FileUpload,
};

export default function CommonPage() {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  let section = queryParams.get("section"); // may be null
  if (section != null) section = section.replace(/\s+/g, "-").toLowerCase();
  const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;

  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");



  // Compute API URL if section exists
  const apiUrl = section ? `${API_BASE_URL}/${encodeURIComponent(section)}` : null;
  console.log( `${API_BASE_URL}/${encodeURIComponent(section)}`);
  useEffect(() => {
    if (!apiUrl) return; // blank area if no section

    const fetchData = async () => {
      try {
        setLoading(true);
        setError("");
        setData(null);

        const response = await fetch(apiUrl);

        if (!response.ok) {
          const errorText = await response.text();
          throw new Error(errorText || "Failed to fetch data");
        }

        const result = await response.json();
        setData(result);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [apiUrl]);

  // Select the component to render dynamically
  const SectionComponent = section ? sectionComponents[section.toLowerCase()] : null;

  return (
    <div style={{ display: "flex", minHeight: "100vh", flexDirection: "column" }}>
      {/* Header */}
      

      <div style={{ display: "flex", flex: 1 }}>
        {/* Sidebar */}
        <SideMenu />

        {/* Main content */}
        <div
          style={{
            flex: 1,
            padding: "25px",
            background: "#f9fafb",
            minHeight: "calc(100vh - 120px)",
          }}
        >
          {/* Blank area if no section */}
          {!section && <div></div>}

          {/* Dynamic section component */}
          {SectionComponent && (
            <SectionComponent data={data} loading={loading} error={error} />
          )}

          {/* Fallback to JSON view if no component */}
          {section && !SectionComponent && data && (
            <pre
              style={{
                background: "white",
                padding: "15px",
                borderRadius: "8px",
                boxShadow: "0 2px 6px rgba(0,0,0,0.1)",
              }}
            >
              {JSON.stringify(data, null, 2)}
            </pre>
          )}
        </div>
      </div>

      {/* Footer */}
      
    </div>
  );
}