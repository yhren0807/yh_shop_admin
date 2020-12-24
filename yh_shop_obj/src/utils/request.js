import axios from 'axios';
import router from '../router';
const service = axios.create({
    // process.env.NODE_ENV === 'development' 来判断是否开发环境
    // easy-mock服务挂了，暂时不使用了
    // baseURL: 'https://www.easy-mock.com/mock/592501a391470c0ac1fab128',
    timeout: 5000
});

service.interceptors.request.use(
    config => {
        config.headers.common['Authorization-token']=localStorage.getItem("token")
        config.headers.common['Authorization-Ip']=localStorage.getItem("userIp")
        return config;
    },
    error => {
        console.log(error);
        return Promise.reject();
    }
);

service.interceptors.response.use(
    response => {
        if (response.data==="noRight"){
            router.replace("*")
            return response;
        }
        let code = response.data.code;
        if (code===225){
            router.replace("/login")
        }
        return response;
    },
    error => {
        console.log(error);
        return Promise.reject();
    }
);

export default service;
