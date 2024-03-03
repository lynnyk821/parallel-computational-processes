with Ada.Text_IO;

procedure Main is

   can_stop : Boolean := false;
   pragma Atomic(can_stop);

   task type Break_Thread;
   task type Main_Thread;

   task body Break_Thread is
   begin
      delay 1.0;
      can_stop := true;
   end Break_Thread;

   task body Main_Thread is
      sum : Long_Long_Integer := 0;
   begin
      loop
         sum := sum + 1;
         exit when can_stop;
      end loop;

      Ada.Text_IO.Put_Line(sum'Img);
   end Main_Thread;

   t1 : Main_Thread;
   t2 : Main_Thread;
   t3 : Main_Thread;
   t4 : Main_Thread;
   b1 : Break_Thread;

begin
   null;
end Main;
