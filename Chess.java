class Chess
{    
   // Initalizing board and coordinate
   static Board gameBoard = new Board (8,8);
   static Coordinate clickSpot;
   
   // Holds where the position of the pieces is
   static String piecePosition[][]= new String [8][8];
   
   // Media player for piece sound
   static String filePath = Chess.class.getProtectionDomain().getCodeSource().getLocation().getPath();
   static MediaPlayer mp = new MediaPlayer(filePath + "move.wav");

   
   // Checks if a move is valid
   public static boolean checkValid(int r1, int c1, int r2, int c2, int count)
   {
      boolean check=false;
      String pieceColour=piecePosition[r1][c1].substring(0,5);
      String pieceColour1=piecePosition[r2][c2].substring(0,5);
      
      // Checks if player turn is true
      if (count%2==0 && pieceColour.equals("black"))
      {
        return false;
      }
      else if (count%2==1 && pieceColour.equals("white"))
      {
        return false;
      }
        
      // Checks if trying to capture own pieces
      if ((pieceColour.equals("black") && pieceColour1.equals("black")) || (pieceColour.equals("white") && pieceColour1.equals("white")))
      {
         return false;
      }
      
      // Checks if square isn't empty
      if (piecePosition[r1][c1].equals("whiteP") || piecePosition[r1][c1].equals("blackP"))
      {
         // Checks if pawn move is valid
         if (checkValidPawn(r1, c1, r2, c2))
         {
            check=true;
         }
      }
      else if (piecePosition[r1][c1].equals("whiteN") || piecePosition[r1][c1].equals("blackN"))
      {
         // Checks if knight move is valid
         if (checkValidKnight(r1, c1, r2, c2))
         {
            check=true;
         }
      }
      else if (piecePosition[r1][c1].equals("whiteB") || piecePosition[r1][c1].equals("blackB"))
      {
         // Checks if bishop move is valid
         if (checkValidBishop(r1, c1, r2, c2))
         {
            check=true;
         }
      }
      else if (piecePosition[r1][c1].equals("whiteR") || piecePosition[r1][c1].equals("blackR"))
      {
         // Checks if rook move is valid
         if (checkValidRook(r1, c1, r2, c2))
         {
            check=true;
         }
      }
      else if (piecePosition[r1][c1].equals("whiteQ") || piecePosition[r1][c1].equals("blackQ"))
      {
         // Checks if Queen move is valid
         if (checkValidQueen(r1, c1, r2, c2))
         {
            check=true;
         }
      }
      else if (piecePosition[r1][c1].equals("whiteK") || piecePosition[r1][c1].equals("blackK"))
      {
         // Checks if Queen move is valid
         if (checkValidKing(r1, c1, r2, c2))
         {
            check=true;
         }
      }
      else
      {
         check=false;
      }
      return check;
   }
   
   // Checks pawn move
   public static boolean checkValidPawn(int r1, int c1, int r2, int c2)
   {
      String pieceColour=piecePosition[r2][c2].substring(0,5);
      
      // Checks if move forward is valid
      if ((c1==c2 && r1-r2==1 && piecePosition[r1][c1].equals("whiteP") && !pieceColour.equals("black")) || (c1==c2 && r2-r1==1 && piecePosition[r1][c1].equals("blackP") && !pieceColour.equals("white")))
      {
         return true;
      }
      // Checks if first row two up move is valid
      else if ((r1==6 && r1-r2==2 && c1==c2 && !pieceColour.equals("black")) || (r1==1 && r2-r1==2 && c1==c2 && !pieceColour.equals("white")))
      {
         return true;
      }
      // Checks if white capture is valid
      else if (piecePosition[r1][c1].equals("whiteP") && Math.abs(c1-c2)==1 && r1-r2==1 && pieceColour.equals("black"))
      {
         return true;
      }
      // Checks if black capture is valid
      else if (piecePosition[r1][c1].equals("blackP") && Math.abs(c1-c2)==1 && r1-r2==-1 && pieceColour.equals("white"))
      {
         return true;
      }
      else 
      {
         return false;
      }
   }
   
   // Checks knight movement
   public static boolean checkValidKnight(int r1, int c1, int r2, int c2)
   {
      // Checks if move is L shaped
      if ((Math.abs(r1-r2)==1 && Math.abs(c1-c2)==2) || (Math.abs(r1-r2)==2 && Math.abs(c1-c2)==1))
      {
         return true;
      }
      else 
      {
         return false;
      }
   }
   
   // Checks valid bishop movement
   public static boolean checkValidBishop(int r1, int c1, int r2, int c2)
   {  
      // Checks if bishop is hopping over other pieces
      int counter=1;
      
      // For going up:
      // Checks going diagonal to top right
      if (r1>r2 && c2>c1)
      {
         for (int i=(r1-1); i>r2; i--)
         {
            if (!piecePosition[i][c1+counter].equals("empty"))
            {
               return false;
            }
            counter++;
         }
      }
      // Checks going diagonal to top left
      if (r1>r2 && c1>c2)
      {
         for (int i=(r1-1); i>r2; i--)
         {
            if (!piecePosition[i][c1-counter].equals("empty"))
            {
               return false;
            }
            counter++;
         }
      }
      
      // For going down:
      // Checks going diagonal to bottom right
      if (r2>r1 && c2>c1)
      {
         for (int i=(r1+1); i<r2; i++)
         {
            if (!piecePosition[i][c1+counter].equals("empty"))
            {
               return false;
            }
            counter++;
         }
      }
      // Checks going diagonal to bottom left 
      if (r2>r1 && c1>c2)
      {
         for (int i=(r1+1); i<r2; i++)
         {
            if (!piecePosition[i][c1-counter].equals("empty"))
            {
               return false;
            }
            counter++;
         }
      }
      
      // Forces bishop to move diagonally
      if (Math.abs(c1-c2)!=0)
      {
         if ((Math.abs(r1-r2)/Math.abs(c1-c2))==1)
         {
            return true;
         }
         else 
         {
            return false;
         }
      }
      else
      {
         return false;
      }
   }
   
   // Checks rook move
   public static boolean checkValidRook(int r1, int c1, int r2, int c2)
   {
      // Checks if rooks are hopping over other pieces 
      
      // For vertical:
      // Checks going up
      if (c1==c2 && r1>r2)
      {
         for (int i=(r1-1); i>r2; i--)
         {
            if (!piecePosition[i][c1].equals("empty"))
            {
               return false;
            }
         }
      }
      // Checks going down
      if (c1==c2 && r2>r1)
      {
         for (int i=(r2-1); i>r1; i--)
         {
            if (!piecePosition[i][c1].equals("empty"))
            {
               return false;
            }
         }
      }
      
      // For horizontal:
      // Checks going right
      if (r1==r2 && c2>c1)
      {
         for (int i=(c1+1); i<c2; i++)
         {
            if (!piecePosition[r1][i].equals("empty"))
            {
               return false;
            }
         }
      }
      // Checks going left
      if (r1==r2 && c1>c2)
      {
         for (int i=(c1-1); i>c2; i--)
         {
            if (!piecePosition[r1][i].equals("empty"))
            {
               return false;
            }  
         }      
      }

      // Checks if move is straight
      if (r1-r2==0 || c1-c2==0)
      {
         return true;
      }
      else
      {
         return false;
      }
   }
      
   // Checks queen move
   public static boolean checkValidQueen(int r1, int c1, int r2, int c2)
   {
      if (checkValidRook(r1,c1,r2,c2) || checkValidBishop(r1,c1,r2,c2))
      {
         return true;
      }
      else
      {
         return false;
      }
   }
   
   // Checks king move
   public static boolean checkValidKing(int r1, int c1, int r2, int c2)
   {
      if ((c1==c2 && Math.abs(r1-r2)==1) || (r1==r2 && Math.abs(c1-c2)==1))      
      {
         return true;
      }
      else if (c1-c2!=0)
      { 
         if ((Math.abs(r1-r2)/Math.abs(c1-c2))==1 && Math.abs(r1-r2)==1 && Math.abs(c1-c2)==1)
         {
            return true;
         }
         else
         {
            return false;
         }
      }
      else
      {
         return false;
      }
   }
   
   // Moves pieces
   public static void movePiece(int r1, int c1, int r2, int c2, int count)
   {
      // Plays sound
      new Thread(new MediaPlayer(filePath + "move.wav")).start();
      
      // Removes the piece
      gameBoard.removePeg(r1, c1);
      
      // Checks pormotion
      if (checkPromotion(r2,c2))
      {
         piecePosition[r1][c1]="empty";
         
         if (count%2==0)
         {
            piecePosition[r2][c2]="whiteQ";
            gameBoard.putPeg(piecePosition[r2][c2], r2, c2);
         }
         else
         {
            piecePosition[r2][c2]="blackQ";
            gameBoard.putPeg(piecePosition[r2][c2], r2, c2);
         }
      }
      else
      {
         gameBoard.putPeg(piecePosition[r1][c1], r2, c2);
         piecePosition[r2][c2]=piecePosition[r1][c1];
         piecePosition[r1][c1]="empty";
      }
   }
   
   // Checks promotion
   public static boolean checkPromotion (int r2, int c2)
   {
      if ((piecePosition[r2][c2].equals("blackP") && r2==7)|| (piecePosition[r2][c2].equals("whiteP") && r2==0))
      {
         return true;
      }
      else
      {
         return false;
      } 
   }
   
   // Checks if the game is over
   public static boolean endGame(int count)
   {
      boolean check=false;
      
      // Checks if either king is dead
      for (int i=0; i<8; i++)
      {
         for (int n=0; n<8; n++)
         {
            // Checks if white king is dead
            if (count%2==0)
            {
               if (piecePosition[i][n].equals("whiteK"))
               {
                  check=true;
               }
            }
            // Checks if black king is dead
            else 
            {
               if (piecePosition[i][n].equals("blackK"))
               {
                  check=true;
               }
            }
         }
      }

      return check;
   }
   
   public static void main(String[] args)
   {   
      // Initalizing
      
         // Variables
         int row1 = 0, col1 = 0, row2 = 0, col2 = 0, counter=0;
         String colourTurn;
         
         // Fills spots as empty
         for (int i=0; i<8; i++)
         {
            for (int n=0; n<8; n++)
            {
               piecePosition[i][n]="empty";
            }
         }
      
         // Sets up position of pawns
         for (int i=0; i<8; i++)
         {
            gameBoard.putPeg("blackP",1,i);
            piecePosition[1][i]="blackP";
 
            gameBoard.putPeg("whiteP",6,i);
            piecePosition[6][i]="whiteP";
         }
         
         // Sets up position of knights
         for (int i=1; i<7; i=i+5)
         {
            gameBoard.putPeg("blackN",0,i);
            piecePosition[0][i]="blackN";
            
            gameBoard.putPeg("whiteN",7,i);
            piecePosition[7][i]="whiteN";
         }
         
         // Sets up positions of bishops
         for (int i=2; i<7; i=i+3)
         {
            gameBoard.putPeg("blackB",0,i);
            piecePosition[0][i]="blackB";
            
            gameBoard.putPeg("whiteB",7,i);
            piecePosition[7][i]="whiteB";
         }
         
         // Sets up position of rooks
         for (int i=0; i<8; i=i+7)
         {
            gameBoard.putPeg("blackR",0,i);
            piecePosition[0][i]="blackR";
            
            gameBoard.putPeg("whiteR",7,i);
            piecePosition[7][i]="whiteR";
         }
         
         // Sets up position of queens
         gameBoard.putPeg("blackQ",0,3);
         piecePosition[0][3]="blackQ";
            
         gameBoard.putPeg("whiteQ",7,3);
         piecePosition[7][3]="whiteQ";
        
         // Sets up position of king
         gameBoard.putPeg("blackK",0,4);
         piecePosition[0][4]="blackK";
            
         gameBoard.putPeg("whiteK",7,4);
         piecePosition[7][4]="whiteK";
         
      // Playing loop
         do
         {
            if (counter%2==0)
            {
               colourTurn="White's";
            }
            else 
            {
               colourTurn="Black's";
            }
            
            gameBoard.displayMessage(colourTurn+" turn (click 1)");
            clickSpot=gameBoard.getClick();
            row1 = clickSpot.getRow();
            col1 = clickSpot.getCol();
            
            gameBoard.displayMessage(colourTurn+" turn (click 2)");
            clickSpot=gameBoard.getClick();
            row2 = clickSpot.getRow();
            col2 = clickSpot.getCol();
            
         // Moves piece
            if (checkValid(row1, col1, row2, col2,counter))
            {
               movePiece(row1,col1,row2,col2,counter);
               counter++;
            }
            else 
            {
               gameBoard.displayMessage("Invalid move try again");
               try        
               {
                  Thread.sleep(1000);
               } 
               catch(InterruptedException ex) 
               {
                  Thread.currentThread().interrupt();
               }
            }
         }
         while (endGame(counter));

         gameBoard.displayMessage(colourTurn+" wins!");
   }
}