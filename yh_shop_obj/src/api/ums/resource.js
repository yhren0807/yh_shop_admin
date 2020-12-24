import request from '../../utils/request';

export const queryResourcesByCatePory = () => {
    return request({
        url: '/yh_shop/umsResourceController/queryResourcesByCatePory',
        method: 'get',

    });
};

export const queryResourceByRoleId =id=> {
    return request({
        url: '/yh_shop/umsResourceController/queryUmsRoleResourceByRoleId',
        method: 'get',
        params:{id:id}

    });
};

export const queryUmsRoleResourceByRoleId =id=> {
    return request({
        url: '/yh_shop/umsRoleResource/queryUmsRoleResourceByRoleId',
        method: 'get',
        params:{id:id}
    });
};

export const saveUmsRoleResourceByRoleId =list=> {
    return request({
        url: '/yh_shop/umsRoleResource/saveUmsRoleResourceByRoleId',
        method: 'post',
        data:list
    });
};


//增删改查方法
export const queryUmsResources =query=> {
    return request({
        url: '/yh_shop/umsResourceController/queryUmsResources',
        method: 'get',
        params:query
    });
};
export const deleteUmsResource = id => {
    return request({
        url: '/yh_shop/umsResourceController/deleteUmsResource',
        method: 'delete',
        params: {id:id}
    });
};
export const queryUmsResourceById = id => {
    return request({
        url: '/yh_shop/umsResourceController/queryUmsResourceById',
        method: 'get',
        params: {id:id}
    });
};
export const saveUmsResource = form => {
    return request({
        url: '/yh_shop/umsResourceController/saveUmsResource',
        method: 'post',
        params:form
    });
};
export const updateUmsResource = form => {
    return request({
        url: '/yh_shop/umsResourceController/updateUmsResource',
        method: 'put',
        params: form
    });
};
