import request from '../../utils/request';

export const saveProduct= query => {
    return request({
        url: '/yh_shop/pmsProductGroup/saveProduct',
        method: 'post',
        data: query
    });
};
