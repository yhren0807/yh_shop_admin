import request from '../../utils/request';

export const deleteBrandData = id => {
    return request({
        url: '/yh_shop/umsHomeBrandController/deleteUmsHomeBrand',
        method: 'delete',
        params:{"id":id}
    });
};
