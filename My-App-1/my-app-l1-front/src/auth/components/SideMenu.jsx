import React, { useEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";

export default function SideMenu() {
  const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const activeSection = queryParams.get("section");

  const [menuItems, setMenuItems] = useState([]);
  const [openMenus, setOpenMenus] = useState({}); // Track which menus are open

  useEffect(() => {
    fetch(`${API_BASE_URL}/link`)
      .then((res) => res.json())
      .then((data) => setMenuItems(data))
      .catch((err) => console.error("Error fetching menu items:", err));
  }, []);

  const toggleMenu = (id) => {
    setOpenMenus((prev) => ({
      ...prev,
      [id]: !prev[id],
    }));
  };

  // Recursive render function
  const renderMenu = (items) => {
    return (
      <ul style={{ listStyle: "none", paddingLeft: "10px" }}>
        {items.map((item) => {
          const hasChildren = item.children && item.children.length > 0;
          const isActive = activeSection === item.title;

          return (
            <li key={item.id} style={{ marginBottom: "5px" }}>
              <div
                style={{
                  display: "flex",
                  alignItems: "center",
                  cursor: hasChildren ? "pointer" : "default",
                }}
                onClick={() => hasChildren && toggleMenu(item.id)}
              >
                {hasChildren && (
                  <span style={{ marginRight: "6px" }}>
                    {openMenus[item.id] ? "▼" : "▶"}
                  </span>
                )}
                <Link
                  to={`/common?section=${item.title}`}
                  style={{
                    display: "block",
                    padding: "8px",
                    borderRadius: "6px",
                    textDecoration: "none",
                    color: "white",
                    background: isActive ? "#3b82f6" : "transparent",
                    fontWeight: isActive ? "bold" : "normal",
                    flex: 1,
                  }}
                >
                  {item.title}
                </Link>
              </div>

              {/* Render children only if the menu is open */}
              {hasChildren && openMenus[item.id] && renderMenu(item.children)}
            </li>
          );
        })}
      </ul>
    );
  };

  return (
    <aside
      style={{
        width: "220px",
        background: "#1f2937",
        color: "white",
        padding: "20px",
        minHeight: "100vh",
      }}
    >
      <h3 style={{ marginBottom: "20px" }}>Menu</h3>
      {renderMenu(menuItems)}
    </aside>
  );
}