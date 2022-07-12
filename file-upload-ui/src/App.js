import React, { useState } from "react";
import FileUpload from "./components/file-upload/file-upload.component";
import { UploadFileBtn } from "./components/file-upload/file-upload.styles";

function App() {
  const [newFiles, setNewFiles] = useState({
    documents: []
  });

  const updateUploadedFiles = (files) => {
    setNewFiles({ ...newFiles, documents: files });
    console.log(files);
    console.log(newFiles);
  }
  const handleSubmit = (event) => {
    event.preventDefault();
    //logic to create new user...
    console.log(event);
    console.log("new files",newFiles);
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <FileUpload
          accept=".docx, .xlsx, .xlsm, .pdf, .jiff, .jpeg"
          label="Profile Image(s)"
          multiple
          updateFilesCb={updateUploadedFiles}
        />
        <UploadFileBtn type="submit">
          <i className="fas fa-file-upload" />
          <span>Upload</span>
        </UploadFileBtn>
      </form>
    </div>
  );
}

export default App;
