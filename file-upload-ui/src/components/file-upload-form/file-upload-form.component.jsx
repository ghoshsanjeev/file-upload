import React, { useRef, useState } from "react";
import fileService from "../../service/file-service";
import FileUpload from "../file-upload/file-upload.component";
import { InputTextArea, LabelText, UploadButtonContainer, UploadFileBtn } from "../file-upload/file-upload.styles";

const FileUploadForm = () => {
    const [newFiles, setNewFiles] = useState({
        documents: []
    });

    const CommentRef = useRef(null);

    const [progress, setProgrss] = useState(0);
    const [message, setMessage] = useState(null);

    const updateUploadedFiles = (files) => {
        setNewFiles({ ...newFiles, documents: files });
        console.log(files);
        console.log(newFiles);
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        //logic to post files...
        console.log(event);
        console.log("new files", newFiles);

        let formData = new FormData();

        for (let idx in newFiles.documents) {
            formData.append("doc", newFiles.documents[idx]);
        }
        formData.append("comment", CommentRef.current.value);

        fileService.post(formData)
            .then((response) => {
                //setMessage(response.data.message);
                let data = response.data;
                if (typeof data === "string") {
                    alert(data);
                }

                //alert(response.data.message);
                //return UploadService.getFiles();
            })
            .catch((error) => {
                console.log('error!')
                let message = error.response.data;
                if (typeof message === "string") {
                    alert(message);
                }
            });

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

                <LabelText>Comment:</LabelText>
                <InputTextArea rows={4} cols={75} style={{ fontSize: 18, color: "blue" }} ref={CommentRef} />

                <UploadButtonContainer>
                    <UploadFileBtn type="submit">
                        <i className="fas fa-file-upload" />
                        <span>Upload</span>
                    </UploadFileBtn>
                </UploadButtonContainer>

            </form>
        </div >
    );
}

export default FileUploadForm;