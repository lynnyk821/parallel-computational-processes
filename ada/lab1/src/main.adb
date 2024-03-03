with Ada.Text_IO; use Ada.Text_IO;

procedure Main is

   Step : Long_Long_Integer := 2;

   Flag : Boolean := False;
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

   procedure Lab1(N: Positive) is
      Threads : Threads_Array(1 .. N);
      Break : Break_Thread;
   begin
      null;
      end Lab;
begin
   Put("Enter the step: ");
   Step := Long_Long_Integer'Value(Get_Line);
   Lab1(10);
   null;
end Main;
