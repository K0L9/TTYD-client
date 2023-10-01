import axios from "axios";

const instance = axios.create({
  baseURL: "/",
  headers: {
    "Content-type": "application/json",
    "Access-Control-Allow-Origin": "*",
    "Access-Control-Allow-Methods": "GET, PUT, POST, DELETE, OPTIONS",
    Authorization: `Bearer ${localStorage.getItem("token")}`,
  },
});

export default instance;
