import request from '../../utils/request';

export const queryResourceCate = () => {
    return request({
        url: '/yh_shop/umsResourceCate/queryResourceCate',
        method: 'get',

    });
};
export const deleteResourceCate = id => {
    return request({
        url: '/yh_shop/umsResourceCate/deleteResourceCate',
        method: 'delete',
        params: {id:id}
    });
};
export const queryUmsResourceCateById = id => {
    return request({
        url: '/yh_shop/umsResourceCate/queryUmsResourceCateById',
        method: 'get',
        params: {id:id}
    });
};
export const saveUmsResourceCate = form => {
    return request({
        url: '/yh_shop/umsResourceCate/saveUmsResourceCate',
        method: 'post',
        params:form
    });
};
export const updateUmsResourceCate = form => {
    return request({
        url: '/yh_shop/umsResourceCate/updateUmsResourceCate',
        method: 'put',
        params: form
    });
};
