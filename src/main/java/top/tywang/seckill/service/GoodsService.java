package top.tywang.seckill.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tywang.seckill.dao.SeckillGoodsDao;
import top.tywang.seckill.domain.SecKillGoods;
import top.tywang.seckill.vo.SeckillGoodsVo;


@Service
public class GoodsService {

    @Autowired
    SeckillGoodsDao seckillGoodsDao;

    public List<SeckillGoodsVo> listGoodsVo() {
        return seckillGoodsDao.listSeckillGoodsVo();
    }

    public SeckillGoodsVo getGoodsVoByGoodsId(long goodsId) {
        return seckillGoodsDao.getSeckillGoodsVoByGoodsId(goodsId);
    }

    public void reduceStock(SeckillGoodsVo goods) {
        SecKillGoods g = new SecKillGoods();
        g.setGoodsId(goods.getId());
        seckillGoodsDao.reduceStock(g);
    }


}
