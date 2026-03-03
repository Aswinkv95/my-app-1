import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import './App.css';
import LoginPage from "./auth/pages/LoginPage";
import Home from "./auth/pages/Home";

function App() {
  return (
     <Router>
      <Routes>
        <Route path="/" element={<LoginPage />} />
          <Route path="/home" element={<Home />} />
        <Route path="/common" element={<Home />} /> {/* Common page included in Home */}
      </Routes>
    </Router>
  );
}

export default App;
