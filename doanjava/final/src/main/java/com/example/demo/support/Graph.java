package com.example.demo.support;

import com.example.demo.entity.GiaoVien;
import com.example.demo.entity.Lichlamviec;
import com.example.demo.entity.LichlamviecPK;
import com.example.demo.entity.Lop;
import com.example.demo.service.GiaoVienService;
import com.example.demo.service.LichlamviecService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class Graph implements Cloneable{
    private List<Vertex> list_vertex = new ArrayList<Vertex>();
    private Map<Integer, Ngaylamviec> color = new HashMap<Integer, Ngaylamviec>();
    @Autowired
    private LichlamviecService lichlamviecService;
    @Autowired
    private GiaoVienService giaoVienService;
    public Graph(){
        this.create_color();
    }
    public void create_color(){
        Integer init = 21;
        while (init < 80)
        {
            Integer tmp = init;
            Integer tiet = tmp % 10;
            tmp = tmp / 10;
            Integer thu = tmp;
            this.color.put(init, new Ngaylamviec(thu,tiet));
            init += 1;
        }
        this.color.remove(30);
        this.color.remove(40);
        this.color.remove(50);
        this.color.remove(60);
        this.color.remove(70);
    }

    public Ngaylamviec getColor(Map<Integer ,Ngaylamviec> l_color)
    {
        Random rd = new Random();
        List<Ngaylamviec> res = new ArrayList<>(l_color.values());
        int num1 = rd.nextInt(l_color.size() - 1);
        return res.get(num1);
    }

    public void tomau() throws
            CloneNotSupportedException
    {
        this.sort_vertex();
        for(int i = 0; i < this.list_vertex.size(); i++)
        {
            Map<String, Map<Integer, Ngaylamviec>> possible = new HashMap<>();
            for(Lop l : this.list_vertex.get(i).getGiaovien().getLops())
            {
                Map<Integer, Ngaylamviec> copy = new HashMap<>(this.color);
                possible.put(l.getTenlop(), copy);
            }

            Map<Integer, GiaoVien> l_near = this.list_vertex.get(i).getNear();
            for(int j = 0; j < this.list_vertex.size(); j++)
            {
                if (l_near.containsKey(this.list_vertex.get(j).getGiaovien().getMagv()))
                {
                    List<Lop> l_j = this.list_vertex.get(j).getGiaovien().getLops();
                    for(Lop l : l_j)
                    {
                        if(possible.containsKey(l.getTenlop()))
                        {
                            List<Lichlamviec> tm = this.list_vertex.get(j).getGiaovien().getLichlamviecs();
                            List<Lichlamviec> tm1 = new ArrayList<>();
                            for (Lichlamviec llv : tm)
                            {
                                if (l.getMalop() == llv.getLop().getMalop())
                                    tm1.add(llv);
                            }
                            if (tm1.size() != 0)
                            {
                                for (Lichlamviec i_lnv : tm1)
                                {
                                    Integer key = (i_lnv.getLichlamviecPK().getThu() * 10) +
                                            i_lnv.getLichlamviecPK().getTiet();
                                    if (possible.get(l.getTenlop()).containsKey(key))
                                        possible.get(l.getTenlop()).remove(key);
                                }
                            }
                        }
                    }
                }
            }
            for(Lop l : this.list_vertex.get(i).getGiaovien().getLops())
            {
                int t = 0;
                while (t < Cons.getLoaiMon_lop(this.list_vertex.get(i).getGiaovien().getMonhoc().getTenmh()))
                {
                    Ngaylamviec nlv = new Ngaylamviec();
                    if (Cons.getLoaiMon_week(this.list_vertex.get(i).getGiaovien().getMonhoc().getTenmh()) == 24)
                    {
                        nlv = this.getColor(possible.get(l.getTenlop()));
                    }else{
                        while(true)
                        {
                            nlv = this.getColor(possible.get(l.getTenlop()));
                            if(nlv.getTiet() == 1 || nlv.getTiet() == 2 || nlv.getTiet() == 3
                            || nlv.getTiet() == 4 || nlv.getTiet() == 5)
                                break;
                        }
                    }
                    LichlamviecPK pk = new LichlamviecPK(this.list_vertex.get(i).getGiaovien().getMagv()
                        ,l.getMalop(), nlv.getThu(), nlv.getTiet()
                    );
                    Lichlamviec llv = new Lichlamviec(pk);
                     this.list_vertex.get(i).getGiaovien().getLichlamviecs().add(llv);
                    Integer key_nlv = (nlv.getThu() * 10) + nlv.getTiet();
                    for (Lop l1 : this.list_vertex.get(i).getGiaovien().getLops())
                    {
                        if (possible.get(l1.getTenlop()).containsKey(key_nlv))
                            possible.get(l1.getTenlop()).remove(key_nlv);
                    }
                    t += 1;
                }
            }
            for (Map.Entry me : possible.entrySet())
            {
                System.out.println("Key: " + me.getKey() + " & Value: " + me.getValue());
            }
            //giaoVienService.save(this.list_vertex.get(i).getGiaovien());
        }


    }

    public void add_vertex(Vertex vetex)
    {
        this.list_vertex.add(vetex);
    }
    public void create_relation()
    {
        Map<String, List<GiaoVien>> l_k = new HashMap<String, List<GiaoVien>>();
        for (Vertex v : this.list_vertex)
        {
            List<Lop> lops = v.getGiaovien().getLops();
            for (Lop l : lops)
            {
                if(l_k.containsKey(l.getTenlop()) == true)
                {
                    l_k.get(l.getTenlop()).add(v.getGiaovien());
                }else{
                    List<GiaoVien> gvs = new ArrayList<GiaoVien>();
                    gvs.add(v.getGiaovien());
                    l_k.put(l.getTenlop(), gvs);
                }
            }
        }

        for (Vertex v : this.list_vertex)
        {
            List<Lop> lops = v.getGiaovien().getLops();
            for (Lop l : lops)
            {
                if (l_k.containsKey(l.getTenlop()) == true)
                {
                    List<GiaoVien> i_k = l_k.get(l.getTenlop());
                    for (GiaoVien g : i_k)
                    {
                        if(v.getGiaovien().getHoten() != g.getHoten() && v.getNear().containsKey(g.getHoten()) != true)
                        {
                            v.getNear().put(g.getMagv(), g);
                        }
                    }
                }
            }
        }
    }
    public void sort_vertex()
    {
        for(int i = 0; i < this.list_vertex.size() - 2; i++)
        {
            for (int j = 1; j < this.list_vertex.size() - 1; j++)
            {
                if (this.list_vertex.get(i).getNear().size() < this.list_vertex.get(j).getNear().size())
                {
                    Vertex tmp = this.list_vertex.get(i);
                    this.list_vertex.set(i,this.list_vertex.get(j));
                    this.list_vertex.set(j, tmp);
                }
            }
        }
    }
}
