import request from '../utils/request';

export const fetchData = query => {
    return request({
        url: './table.json',
        method: 'get',
        params: query
    });
};

export const queryMenu = username => {
    return request({
        url: '/yh_shop/umsAdminController/queryMenu',
        method: 'get',
        params: {username:username}
    });
};

export const loginOut = token => {
    return request({
        url: '/yh_shop/umsAdminController/loginOut',
        method: 'delete',
        params: {token:token}
    });
};