import request from '../../utils/request';

export const queryCategory = query => {
    return request({
        url: '/yh_shop/umsCategorp/queryCategory',
        method: 'get',
        params: query
    });
};
export const queryParent =() => {
    return request({
        url: '/yh_shop/umsCategorp/queryParent',
        method: 'get',
    });
};
export const saveCategory = form => {
    return request({
        url: '/yh_shop/umsCategorp/saveCategory',
        method: 'post',
        params: form
    });
};
export const queryById = id => {
    return request({
        url: '/yh_shop/umsCategorp/queryById',
        method: 'get',
        params: {id:id}
    });
};
export const updateCategory = form => {
    return request({
        url: '/yh_shop/umsCategorp/updateCategory',
        method: 'put',
        params: form
    });
};
export const fetchQueryCateOptions = () => {
    return request({
        url: '/yh_shop/umsCategorp/queryCateOptions',
        method: 'get',
    });
};
