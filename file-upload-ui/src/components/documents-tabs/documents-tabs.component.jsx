import React from "react";
import FileUploadForm from "../file-upload-form/file-upload-form.component";
import { STab, STabs, STabList, STabPanel } from "./documents-tabs.styles";

const DocumentsTabs = () => (
    <STabs
        selectedTabClassName='is-selected'
        selectedTabPanelClassName='is-selected'
    >
        <STabList>
            <STab>Upload Documents</STab>
            <STab>Download Documents</STab>
        </STabList>
        <STabPanel><FileUploadForm /></STabPanel>
        <STabPanel>Panel 2</STabPanel>
    </STabs>
);

export default DocumentsTabs;
