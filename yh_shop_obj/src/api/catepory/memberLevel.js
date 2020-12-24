import request from '../../utils/request';

export const queryMemberLevel = (id) => {
    return request({
        url: '/yh_shop/umsMemLevel/queryMemberLevel',
        method: 'get',
        params:{id:id}
    });
};
