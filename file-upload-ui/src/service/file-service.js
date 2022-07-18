import http from "./http-service";


class FileService {

    post(formData) {

        let username = "user1";
        let password = "password";

        return http.post("/files", formData, {
            headers: {
                "Authorization": "Basic " + btoa(username + ":" + password)
            },
        });

    };

    get() {
        return http.get("/files");
    };
}

export default new FileService();