package web.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "thread_db")
public class Thread_db {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

    @Column(name = "thread")
    private String thread;

    @Column(name = "d_hole")
    private double dhole_mm;

    @Column(name = "d_head")
    private double dHead_mm;

    @Column(name = "step_thread")
    private double StepThread_mm;

    @Column(name = "d_midlethread")
    private double dMidlethread_mm;

    @Column(name = "d_bolt")
    private double dBolt_mm;

    @OneToMany(mappedBy = "thread",fetch = FetchType.LAZY)
    private List<Moments_db> moments_db;

//    @Column(name = "step_thread")
//    private  String stepThread;
}
