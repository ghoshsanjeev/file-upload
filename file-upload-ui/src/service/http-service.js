import axios from "axios";

export default axios.create({
    baseURL: "http://localhost:7777",
    // headers: {
    //   "Content-type": "application/json"
    // }
    
  });