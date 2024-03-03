with Ada.Text_IO; use Ada.Text_IO;

procedure Main is

   N : Integer := 5;
   Step : Long_Long_Integer := 1;
   Can_Stop : Boolean := False;

   pragma Atomic(Can_Stop);

   task type Break_Thread;
   task type Main_Thread;

   type Threads_Array is array (Positive range <>) of Main_Thread;

   task body Break_Thread is
   begin
      delay 1.0;
      Can_Stop := True;
   end Break_Thread;

   task body Main_Thread is
      Sum : Long_Long_Integer := 0;
   begin
      loop
         Sum := Sum + Step;
         exit when Can_Stop;
      end loop;

      Put_Line(Sum'Img);
   end Main_Thread;

   Threads : Threads_Array(1 .. N);
   B : Break_Thread;

begin
   Put("Enter the step: ");
   Step := Long_Long_Integer'Value(Get_Line);
   null;
end Main;
