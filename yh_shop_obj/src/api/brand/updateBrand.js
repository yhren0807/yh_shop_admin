import request from '../../utils/request';

export const updateBrandData = brand => {
    return request({
        url: '/yh_shop/umsHomeBrandController/updateUmsHomeBrand',
        method: 'put',
        params:brand
    });
};
