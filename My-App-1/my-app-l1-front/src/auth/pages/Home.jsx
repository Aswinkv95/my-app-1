import React from "react";
import Header from "../components/Header";
import Footer from "../components/Footer";
import CommonPage from "./CommonPage";

export default function Home() {
  return (
    <div>
      <Header />
      <main style={{ minHeight: "calc(100vh - 120px)" }}>
        <CommonPage />
      </main>
      <Footer />
    </div>
  );
}