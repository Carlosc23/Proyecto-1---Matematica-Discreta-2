#/usr/bin/env python
#Importar librerias necesarias
import pygame, random
from pygame.locals import *
import math

'''
Carlos Calderon 15219
Julio Barahona 141206
Diego Castaneda 15151
Matematica Discreta 2
'''
#Clase que representa el laberinto
class Laberinto:
 #Constructor la clase
 def __init__(self, LaberintoLayer, solveLayer,opc,opc2):
  self.LaberintoArray = []
  self.state = 'c'  
  self.mLayer = LaberintoLayer 
  self.sLayer = solveLayer
  self.mLayer.fill((0, 0, 0, 0))
  self.sLayer.fill((0, 0, 0, 0))
  self.opc=opc
  self.opc2=opc2
  p=1
  p2=1
  q=0
  q2=0
  div1=0
  div2=0
  o=False
  o2=False
  if opc>=60:
      o=True
      while opc-60>=q: 
          q=60*p
          p+=1
  if opc2>=60:
      o2=True
      print "hola"
      while opc2-60>=q2:
          q2=60*p2
          p2+=1
 # Aqui se pintan los cuadros
  for y in xrange(40): 
   pygame.draw.line(self.mLayer, (65,255,91), (0, y*8), (480, y*8))
   for x in xrange(60):
    self.LaberintoArray.append(0x0)
    if ( y == 0 ):
     pygame.draw.line(self.mLayer, (65,255,91), (x*8,0), (x*8,320))
  if o:   
      pygame.draw.rect(self.sLayer, (65,255,91), Rect((opc-q)*8-8,p*8-8,8,8))
  if (o==False):
      pygame.draw.rect(self.sLayer, (65,255,91), Rect((opc)*8-8,0,8,8))
  if o2:
      print "nnn"
      pygame.draw.rect(self.sLayer, (255,0,255,255), Rect(((opc2-q2)*8-8,p2*8-8,8,8))) 
  if (o2==False):
      pygame.draw.rect(self.sLayer, (255,0,255,255), Rect(((opc2)*8-8,0,8,8)))
  # Seccion del laberinto
  self.totalCells = 2400 # 40 * 60
  self.wallList = []
  self.currentCell = random.randint(0, self.totalCells-1)
  c = self.currentCell
  mz = self.LaberintoArray[c]
  self.LaberintoArray[c] |= 0x00F0 # Ahora forma parte del laberinto
  self.wallList.append((c,0))
  self.wallList.append((c,1))
  self.wallList.append((c,2))
  self.wallList.append((c,3)) # llenar paredes
  self.visitedCells = 1
  self.cellStack = []
  self.compass = [(-1,0),(0,1),(1,0),(0,-1)]

  # Metodo donde se implementa prim y se resuelve laberinto
  #Inspirado en el autor: 	Cthulhu32 y en Mateus Zitelli.
 def update(self,final,inicial):
  self.final=final
  self.inicial=inicial
  if self.state == 'c':
    #Implementacion de prim
   if len(self.wallList) <= 0:
    self.currentCell = inicial # establecer el actual en el tope de la izquierda
    self.cellStack = []
    self.state = 's'
    return
   moved = False
   while(len(self.wallList) > 0 and moved == False):
    # Tomar un muro de manera aleatoria
    wi = random.randint(0, len(self.wallList)-1)
    self.currentCell = self.wallList[wi][0]
    x = self.currentCell % 60
    y = self.currentCell / 60
    dir = self.wallList[wi][1]
    nx = x + self.compass[dir][0]
    ny = y + self.compass[dir][1]
    nidx = ny*60+nx
    dx = x*8
    dy = y*8
    direction = 1 << dir 
    if ((nx >= 0) and (ny >= 0) and (nx < 60) and (ny < 40)):
     if (self.LaberintoArray[nidx] & 0x00F0) == 0:  
      if direction & 1:
       self.LaberintoArray[nidx] |= (4)
       pygame.draw.line(self.mLayer, (0,0,0,0), (dx,dy+1),(dx,dy+7))
      elif direction & 2:
       self.LaberintoArray[nidx] |= (8)
       pygame.draw.line(self.mLayer, (0,0,0,0), (dx+1,dy+8),(dx+7,dy+8))
      elif direction & 4:
       self.LaberintoArray[nidx] |= (1)
       pygame.draw.line(self.mLayer, (0,0,0,0), (dx+8,dy+1),(dx+8,dy+7))
      elif direction & 8:
       self.LaberintoArray[nidx] |= (2)
       pygame.draw.line(self.mLayer, (0,0,0,0), (dx+1,dy),(dx+7,dy))
      self.LaberintoArray[self.currentCell] |= direction
      self.LaberintoArray[(ny*60+nx)] |= 0x00F0 # marcar como parte del laberinto
      # Agregar muros a la lista
      self.wallList.append((ny*60+nx,0))
      self.wallList.append((ny*60+nx,1))
      self.wallList.append((ny*60+nx,2))
      self.wallList.append((ny*60+nx,3))
      moved = True
    self.wallList.remove(self.wallList[wi])
    # Si ya cambio de estado
  elif self.state == 's':
   if self.currentCell == (final-1): # Si ya se encontro la salida  
    self.state = 'r'
    return
   moved = False
   while(moved == False):
    x = self.currentCell % 60
    y = self.currentCell / 60
    neighbors = []
    directions = self.LaberintoArray[self.currentCell] & 0xF
    for i in xrange(4):
     if (directions & (1<<i)) > 0:
      nx = x + self.compass[i][0]
      ny = y + self.compass[i][1]
      if ((nx >= 0) and (ny >= 0) and (nx < 60) and (ny < 40)):     
       nidx = ny*60+nx
       if ((self.LaberintoArray[nidx] & 0xFF00) == 0): # Asegurarse que no hay backtracking
        neighbors.append((nidx,1<<i))
    if len(neighbors) > 0:
     idx = random.randint(0,len(neighbors)-1)
     nidx,direction = neighbors[idx]
     dx = x*8
     dy = y*8
     if direction & 1:
      self.LaberintoArray[nidx] |= (4 << 12)
     elif direction & 2:
      self.LaberintoArray[nidx] |= (8 << 12)
     elif direction & 4:
      self.LaberintoArray[nidx] |= (1 << 12)
     elif direction & 8:
      self.LaberintoArray[nidx] |= (2 << 12)
     pygame.draw.rect(self.sLayer, (255,255,0), Rect(dx,dy,8,8))
     self.LaberintoArray[self.currentCell] |= direction << 8
     self.cellStack.append(self.currentCell)
     self.currentCell = nidx
     moved = True
    else:
     pygame.draw.rect(self.sLayer, (0,0,128), Rect((x*8),(y*8),8,8))
     self.LaberintoArray[self.currentCell] &= 0xF0FF # No solucion
     self.currentCell = self.cellStack.pop()
  elif self.state == 'r':
   self.__init__(self.mLayer,self.sLayer,self.opc,self.opc2)

 def draw(self, screen):
  screen.blit(self.sLayer, (0,0))
  screen.blit(self.mLayer, (0,0))

#Metodo main
def main():
 #Solicitar ingreso de datos   
 opc = input('Escoge una posicion inicial (1-2400): ')
 opc2 = input('Escoge una posicion final (1-2400): ')
 #Inicializar componentes pygame
 pygame.init()
 screen = pygame.display.set_mode((480, 320))
 pygame.display.set_caption('Laberinto')
 background = pygame.Surface(screen.get_size())
 background = background.convert()
 background.fill((0, 0, 0))
 LaberintoLayer = pygame.Surface(screen.get_size())
 LaberintoLayer = LaberintoLayer.convert_alpha()
 LaberintoLayer.fill((0, 0, 0, 0))
 solveLayer = pygame.Surface(screen.get_size())
 solveLayer = solveLayer.convert_alpha()
 solveLayer.fill((0, 0, 0, 0))
 newLaberinto = Laberinto(LaberintoLayer,solveLayer,opc,opc2)
 screen.blit(background, (0, 0))
 pygame.display.flip()
 clock = pygame.time.Clock()
#Loop para mantener corriendo el programa
 while True:
  clock.tick(100)
  for event in pygame.event.get():
   if event.type == QUIT:
    return
   elif event.type == KEYDOWN and event.key == K_ESCAPE:
    return
  newLaberinto.update(opc2,opc)
  screen.blit(background, (0, 0))
  newLaberinto.draw(screen)
  pygame.display.flip()
#Invocar al metodo principal
if __name__ == '__main__': main()
