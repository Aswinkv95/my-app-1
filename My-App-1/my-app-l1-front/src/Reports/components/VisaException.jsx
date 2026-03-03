import React, { useState,useEffect  } from "react";


export default function VisaException() {
  const [fromDate, setFromDate] = useState("");
  const [toDate, setToDate] = useState("");
  const [records, setRecords] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

      // Reset all section-related states whenever section changes
 // Reset form and table whenever this section mounts
  useEffect(() => {
    setFromDate("");
    setToDate("");
    setRecords([]);
    setLoading(false);
    setError("");
  }, []);

    // Convert yyyy-mm-dd -> dd-MM-yyyy
  const formatDateDDMMYYYY = (dateString) => {
    if (!dateString) return "";
    const [year, month, day] = dateString.split("-");
    return `${day}-${month}-${year}`;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!fromDate || !toDate) {
      alert("Please select both dates");
      return;
    }

    if (fromDate > toDate) {
      alert("From Date cannot be greater than To Date");
      return;
    }

    try {
      setLoading(true);
      setError("");
      setRecords([]);

     // Convert dates to dd-MM-yyyy
    const from = formatDateDDMMYYYY(fromDate);
    const to = formatDateDDMMYYYY(toDate);

    // Use URLSearchParams to encode form parameters
    const bodyParams = new URLSearchParams();
    bodyParams.append("fromDate", from);
    bodyParams.append("toDate", to);

    const response = await fetch(`${process.env.REACT_APP_API_BASE_URL}/visa-exception`, {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
      },
      body: bodyParams.toString(), // parameters go in the body
    });

      if (!response.ok) {
        throw new Error("Failed to fetch data");
      }

      const result = await response.json();

      // Ensure it's always an array
      setRecords(Array.isArray(result) ? result : []);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  // Dynamically extract table columns
  const columns =
    records.length > 0 ? Object.keys(records[0]) : [];

  return (
    <div  style={{ textAlign: "center" }}>
      <h2>Visa Exception</h2>

      {/* Date Range Form */}
      <form onSubmit={handleSubmit} style={{ marginBottom: "10px" }}>
  <div
    style={{
       display: "flex",
    flexDirection: "column",
      gap: "25px",
      alignItems: "center",
    }}
  >
    <div>
      <label>From Date: </label>
      <input
        type="date"
        value={fromDate}
        onChange={(e) => setFromDate(e.target.value)}
      />
    </div>
    <div>
      <label>To Date: </label>
      <input
        type="date"
        value={toDate}
        onChange={(e) => setToDate(e.target.value)}
      />
    </div>

    <div>
      <button type="submit">Submit</button>
    </div>
  </div>
</form>

      {/* Status */}
      {loading && <p>Loading...</p>}
      {error && <p style={{ color: "red" }}>{error}</p>}

      {/* Dynamic Table */}
      {records.length > 0 && (
        <table
          border="1"
          cellPadding="10"
          style={{ width: "100%", background: "white" }}
        >
          <thead>
            <tr>
              {columns.map((column) => (
                <th key={column}>
                  {column.replace(/_/g, " ").toUpperCase()}
                </th>
              ))}
            </tr>
          </thead>

          <tbody>
            {records.map((row, rowIndex) => (
              <tr key={rowIndex}>
                {columns.map((column) => (
                  <td key={column}>
                    {row[column] !== null
                      ? row[column].toString()
                      : ""}
                  </td>
                ))}
              </tr>
            ))}
          </tbody>
        </table>
      )}

     
    </div>
  );
}