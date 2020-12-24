import request from '../../utils/request';

export const queryUmsRole = query => {
    return request({
        url: '/yh_shop/umsRoleController/queryUmsRole',
        method: 'get',
        params: query
    });
};
export const saveUmsRole = form => {
    return request({
        url: '/yh_shop/umsRoleController/saveUmsRole',
        method: 'post',
        params: form
    });
};
export const queryUmsRoleById = id => {
    return request({
        url: '/yh_shop/umsRoleController/queryUmsRoleById',
        method: 'get',
        params: {id:id}
    });
};
export const updateUmsRole = form => {
    return request({
        url: '/yh_shop/umsRoleController/updateUmsRole',
        method: 'put',
        params: form
    });
};
export const deleteUmsRole = id => {
    return request({
        url: '/yh_shop/umsRoleController/deleteUmsRole',
        method: 'delete',
        params: {id:id}
    });
};

export const queryByAdminIdUmsRole = id => {
    return request({
        url: '/yh_shop/umsAdminRoleRelationController/queryByAdminIdUmsRole',
        method: 'get',
        params: {id:id}
    });
};
export const saveAdminUmsRole = data => {
    return request({
        url: '/yh_shop/umsAdminRoleRelationController/saveAdminUmsRole',
        method: 'post',
        data: data
    });
};


