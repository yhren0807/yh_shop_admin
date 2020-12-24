import request from '../../utils/request';

export const queryUmsAdmin = query => {
    return request({
        url: '/yh_shop/umsAdminController/queryUmsAdmin',
        method: 'get',
        params: query
    });
};

export const saveUmsAdmin = form => {
    return request({
        url: '/yh_shop/umsAdminController/saveUmsAdmin',
        method: 'post',
        params: form
    });
};
export const queryUmsAdminById = id => {
    return request({
        url: '/yh_shop/umsAdminController/queryUmsAdminById',
        method: 'get',
        params: {id:id}
    });
};
export const updateUmsAdmin = form => {
    return request({
        url: '/yh_shop/umsAdminController/updateUmsAdmin',
        method: 'put',
        params: form
    });
};
export const deleteUmsAdmin = id => {
    return request({
        url: '/yh_shop/umsAdminController/deleteUmsAdmin',
        method: 'delete',
        params: {id:id}
    });
};