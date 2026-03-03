import React from "react";
import { Link } from "react-router-dom";

export default function Header() {
  return (
    <header style={{ background: "#282c34", color: "#fff", padding: "10px" }}>
      <h1>My Bank App</h1>
      <nav>
        <Link to="/home" style={{ marginRight: "15px", color: "#fff" }}>Home</Link>
        <Link to="/logout" style={{ marginRight: "15px", color: "#fff" }}>Logout</Link>
      </nav>
    </header>
  );
}