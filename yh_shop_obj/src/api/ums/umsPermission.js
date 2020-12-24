import request from '../../utils/request';

export const queryUmsPermission = query => {
    return request({
        url: '/yh_shop/umsPermissionController/queryUmsPermission',
        method: 'get',
        params: query
    });
};
export const saveUmsPermission = form => {
    return request({
        url: '/yh_shop/umsPermissionController/saveUmsPermission',
        method: 'post',
        params: form
    });
};
export const queryUmsPermissionById = id => {
    return request({
        url: '/yh_shop/umsPermissionController/queryUmsPermissionById',
        method: 'get',
        params: {id:id}
    });
};
export const updateUmsPermission = form => {
    return request({
        url: '/yh_shop/umsPermissionController/updateUmsPermission',
        method: 'put',
        params: form
    });
};
export const deleteUmsPermission = id => {
    return request({
        url: '/yh_shop/umsPermissionController/deleteUmsPermission',
        method: 'delete',
        params: {id:id}
    });
};