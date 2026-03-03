import React from "react";
import LoginForm from "../components/LoginForm";

export default function LoginPage() {
  const handleLogin = ({ username, password }) => {
    console.log("Login attempt:", username, password);
    // Call your API here
    alert(`Logged in as ${username}`);
  };

  return (
    <div style={{ paddingTop: "50px" }}>
      <LoginForm onSubmit={handleLogin} />
    </div>
  );
}