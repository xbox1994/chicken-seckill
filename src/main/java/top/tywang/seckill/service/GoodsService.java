package top.tywang.seckill.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tywang.seckill.dao.SeckillGoodsDao;
import top.tywang.seckill.domain.SecKillGoods;
import top.tywang.seckill.vo.SecKillGoodsVo;


@Service
public class GoodsService {

    @Autowired
    SeckillGoodsDao seckillGoodsDao;

    public List<SecKillGoodsVo> listGoodsVo() {
        return seckillGoodsDao.listSeckillGoodsVo();
    }

    public SecKillGoodsVo getGoodsVoByGoodsId(long goodsId) {
        return seckillGoodsDao.getSeckillGoodsVoByGoodsId(goodsId);
    }

    public void reduceStock(SecKillGoodsVo goods) {
        SecKillGoods g = new SecKillGoods();
        g.setGoodsId(goods.getId());
        seckillGoodsDao.reduceStock(g);
    }


}
