import axios from 'axios';

const http = axios.create({
  baseURL: process.env.VUE_APP_API,
});

export const setHeader = (param) => {
  http.defaults.headers.Authorization = param === null
    ? null : `${param.token_type} ${param.access_token}`;
};

export default http;
