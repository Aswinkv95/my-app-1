import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function LoginForm({ onSubmit }) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();
const API_BASE_URL = process.env.REACT_APP_API_BASE_URL;


const handleSubmit = async (e) => {
  e.preventDefault();
  try {
    console.log("API:", API_BASE_URL); 
    const response = await fetch(`${API_BASE_URL}/login`, {
  // const response = await fetch(`http://localhost:8080/BANK/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ username, password }),
    });

      if (!response.ok) throw new Error("Login failed");

      const data = await response.json(); // or text if backend returns string
      setMessage(`Server response: ${JSON.stringify(data)}`);
      console.log("Server response:", data);

       navigate("/home");

    } catch (err) {
      setMessage(err.message);
      console.error(err);
    }
  };

  return (
    <form onSubmit={handleSubmit} style={{ maxWidth: "400px", margin: "auto" }}>
      <h2>Login</h2>
      <input
        type="username"
        placeholder="username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
        required
        style={{ width: "100%", padding: "8px", marginBottom: "10px" }}
      />
      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        required
        style={{ width: "100%", padding: "8px", marginBottom: "10px" }}
      />
      <button type="submit" style={{ width: "100%", padding: "10px" }}>Login</button>
    </form>
  );
}