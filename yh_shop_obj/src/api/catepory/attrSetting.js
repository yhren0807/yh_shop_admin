import request from '../../utils/request';

export const queryAttributeSetting = query => {
    return request({
        url: '/yh_shop/productAttribute/queryAttributeSetting',
        method: 'get',
        params: query
    });
};
export const saveAttributeSetting = form => {
    return request({
        url: '/yh_shop/productAttribute/saveAttributeSetting',
        method: 'post',
        params: form
    });
};
export const updateAttributeSetting  =(query) => {
    return request({
        url: '/yh_shop/productAttribute/updateAttributeSetting',
        method: 'put',
        params: query
    });
};
export const queryAttributeSettingById = id => {
    return request({
        url: '/yh_shop/productAttribute/queryAttributeSettingById',
        method: 'get',
        params: {id:id}
    });
};