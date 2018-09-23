import axios from 'axios';

axios.defaults.baseURL = process.env.VUE_APP_API;

export const setAxiosHeader = (param) => {
  const str = (param === null) ? null : `${param.token_type} ${param.access_token}`;
  axios.defaults.headers.common.Authorization = str;
};

export default setAxiosHeader;
