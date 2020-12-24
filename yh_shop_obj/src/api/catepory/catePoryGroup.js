import request from '../../utils/request';

export const queryCatePoryGrop = query => {
    return request({
        url: '/yh_shop/pmsProductGroup/queryCatePoryGrop',
        method: 'get',
        params: query
    });
};
export const queryProductAll = id => {
    return request({
        url: '/yh_shop/pmsProductGroup/queryProductAll',
        method: 'get',
        params:{productId:id}
    });
};