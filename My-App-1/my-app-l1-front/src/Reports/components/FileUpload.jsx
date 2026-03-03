import React, { useState } from "react";

export default function FileUpload() {
  const [network, setNetwork] = useState("");
  const [file, setFile] = useState(null);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const networks = [
    "VISA_NETWORK",
    "MASTER_NETWORK",
    "RUPAY_NETWORK",
  ];

  const handleFileChange = (e) => {
    setError("");
    setSuccess("");

    const selectedFile = e.target.files[0];
    if (!selectedFile) return;

    if (!network) {
      setError("Please select a network first.");
      return;
    }

    const fileName = selectedFile.name.substring(0, 3).toUpperCase();
    const networkPrefix = network.substring(0, 3).toUpperCase();

    if (fileName !== networkPrefix) {
      setError(
        `File name must start with '${networkPrefix}'. Selected file starts with '${fileName}'.`
      );
      setFile(null);
      return;
    }

    setFile(selectedFile);
    setSuccess("File validated successfully.");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!file || !network) {
      setError("Please select network and valid file.");
      return;
    }

    try {
      const formData = new FormData();
      formData.append("file", file);
      formData.append("network", network);

      const response = await fetch(
        `${process.env.REACT_APP_API_BASE_URL}/file-upload`,
        {
          method: "POST",
          body: formData,
        }
      );

      if (!response.ok) {
        throw new Error("Upload failed");
      }

      setSuccess("File uploaded successfully.");
      setFile(null);
      setNetwork("");
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div style={{ textAlign: "center" }}>
      <h2>File Upload</h2>

      <form onSubmit={handleSubmit}>
        <div style={{ marginBottom: "15px" }}>
          <label>Select Network: </label>
          <select
            value={network}
            onChange={(e) => {
              setNetwork(e.target.value);
              setFile(null);
              setError("");
              setSuccess("");
            }}
          >
            <option value="">-- Select Network --</option>
            {networks.map((net) => (
              <option key={net} value={net}>
                {net}
              </option>
            ))}
          </select>
        </div>

        <div style={{ marginBottom: "15px" }}>
          <input type="file" onChange={handleFileChange} />
        </div>

        <button type="submit">Upload</button>
      </form>

      {error && <p style={{ color: "red" }}>{error}</p>}
      {success && <p style={{ color: "green" }}>{success}</p>}
    </div>
  );
}