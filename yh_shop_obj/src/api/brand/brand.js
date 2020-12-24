import request from '../../utils/request';

export const fetchBrandData = query => {
    return request({
        url: '/yh_shop/umsHomeBrandController/queryUmsHomeBrand',
        method: 'get',
        params: query
    });
};
export const queryDescription = () => {
    return request({
        url: '/yh_shop/umsHomeBrandController/queryDescription',
        method: 'get',
    });
};