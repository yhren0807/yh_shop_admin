import request from '../../utils/request';

export const queryAttrType = query => {
    return request({
        url: '/yh_shop/attributeCategory/queryAttributeCategory',
        method: 'get',
        params: query
    });
};
export const saveAttrType  =(query) => {
    return request({
        url: '/yh_shop/attributeCategory/saveAttributeSetting',
        method: 'post',
        params: query
    });
};