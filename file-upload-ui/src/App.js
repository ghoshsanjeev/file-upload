import React, { useState } from "react";
import FileUpload from "./components/file-upload/file-upload.component";
import { UploadButtonContainer, UploadFileBtn } from "./components/file-upload/file-upload.styles";

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
    console.log("new files", newFiles);
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <FileUpload
          accept=".docx, .xlsx, .xlsm, .pdf"
          label="Document(s)"
          multiple
          updateFilesCb={updateUploadedFiles}
        />
        <UploadButtonContainer>
          <UploadFileBtn type="submit">
            <i className="fas fa-file-upload" />
            <span>Upload</span>
          </UploadFileBtn>
        </UploadButtonContainer>
      </form>
    </div>
  );
}

export default App;
