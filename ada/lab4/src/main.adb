with Ada.Text_IO; use Ada.Text_IO;

with GNAT.Semaphores; use GNAT.Semaphores;

procedure Main is
   Number_Of_Philosopher : Integer := 5;

   Forks : array (1..Number_Of_Philosopher) of Counting_Semaphore(1, Default_Ceiling);

   task type Philosopher is
      entry Start(Id : Integer);
   end Philosopher;

   task body Philosopher is
      Id : Integer;
      Id_Left_Fork, Id_Right_Fork : Integer;
   begin
      accept Start (Id : in Integer) do
         Philosopher.Id := Id;
      end Start;
      Id_Left_Fork := Id;
      Id_Right_Fork := Id rem Number_Of_Philosopher + 1;

      for I in 1..4 loop
         Put_Line("Philosopher " & Id'Img & " thinking");

         if Id = Number_Of_Philosopher then
            Forks(Id_Right_Fork).Seize;
            Put_Line("Philosopher " & Id'Img & " took right fork");
            Forks(Id_Left_Fork).Seize;
            Put_Line("Philosopher " & Id'Img & " took left fork");
         else
            Forks(Id_Left_Fork).Seize;
            Put_Line("Philosopher " & Id'Img & " took left fork");
            Forks(Id_Right_Fork).Seize;
            Put_Line("Philosopher " & Id'Img & " took right fork");
         end if;

         Put_Line("Philosopher " & Id'Img & " - eating" & I'Img);

         Forks(Id_Left_Fork).Release;
         Forks(Id_Right_Fork).Release;

         Put_Line("Philosopher " & Id'Img & " put forks. Back to thinking...");
      end loop;
   end Philosopher;

   Philosophers : array (1..Number_Of_Philosopher) of Philosopher;
Begin
   for I in Philosophers'Range loop
      Philosophers(I).Start(I);
   end loop;
end Main;
