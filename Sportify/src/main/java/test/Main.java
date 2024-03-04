//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package test;

import entities.Reservation;
import java.util.Date;

import java.sql.SQLException;

import entities.Terrain;
import services.ServiceReservation;
import services.ServiceTerrain;
import utils.DB;

public class Main {
    public Main() {
    }

    public static void main(String[] args) throws SQLException {
        DB conn1 = DB.getInstance();

        Date maintenant = new Date();

       /* new Terrain(22, "Kenza","dsdssd","DSJKDBS",097.89);
        Terrain p3 = new Terrain(22, "Kenza","dsdssd","DSJKDBS",2131.2);
*/

        ServiceReservation r = new ServiceReservation();
        ServiceTerrain t = new ServiceTerrain();

        // System.out.println( t.afficher().toString());

       // Terrain c = new Terrain(2, "Kenza","Tunis","aa",33.2,"fdsfsdf");

         //t.ajouter(c);


        //Reservation z = new Reservation(3,1,2, maintenant,"sss");
       // Terrain k = new Terrain(2,2, "sss","aa","axxa",35.2);

        //r.ajouter(z);
  // r.modifierReservation(z);
       // t.modifier(k);
       // t.modifertr(k);

       // r.supprimer(3);

        //t.supprimer(1);
       System.out.println( t.afficher().toString());

       //System.out.println( t.afficher().toString());

       // t.supprimer(1);

      /*  try {
            t.modifier(p3);
        } catch (SQLException var7) {
            System.out.println(var7.getMessage());
        }
*/


    }
}
