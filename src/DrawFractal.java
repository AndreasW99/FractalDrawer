

public class DrawFractal
{
private double x1=0.0;
private double y1=0.0;
private double drawingDir=0.0;
private double length=1.0;
private double angle;
private double scalingFactor;
private double x2,y2;
private final static int DEFAULT = 1000;
private LineInfo[] lines;
private Integer lineCount=0;
private CurrentPointInfo CP;
private double Xmin=0.0;
private double Xmax=0.0;
private double Ymin=0.0;
private double Ymax=0.0;
private String symbol;
private String fractalString;

// constructor
public DrawFractal(String fractalString,double scalingFactor,double angle) {
this.fractalString=fractalString;
this.scalingFactor=scalingFactor;
this.angle=angle;
}

// Compute all lines of fractal
public void computeLines() {
angle*=Math.PI/180.0; // convert to radiant
// Array of line objects
lines=new LineInfo[DEFAULT];
double mag;


//object "s" is declared here from stackADT
StackADT<CurrentPointInfo> s = new LinkedStack<CurrentPointInfo>();

//if the symbol is f or g, this means draw a line of some length in some drawing direction (that can be modified by constants "-" and "+" which
//add or subtract some angle from the drawing direction). "F" and "G" 
//do the same thing but using "F" and "G" allows multiple rules for the fractals to be combined
if ((symbol == "F") || (symbol == "G"))
for (int i =0; i<fractalString.length();i++)		//this for loop goes from 0 up and one less than the length of the fractal String.
{
	
	
	{
		//the code below is in particular meant to process character by character. 
		symbol = fractalString.substring(i, i+1);
		x2 = x1+length*Math.cos(drawingDir);							//y2 and x2 are considered the end points and thus can compute
		y2 = y1+length*Math.sin(drawingDir);							//the minimum and maximum of both y and x values as we process the string. 
		LineInfo lineData = new LineInfo(x1,y1,x2,y2);
		lines[i] = lineData;										//store the line data in the lines array.
		UpdateXminYminXmaxYmax(x1,y1,x2,y2);						//all x and y variables are updated using a previous declared function.
		}
	if(symbol == "+")							//symbols in this program means the symbols including "F" and "G", 
	{											//these symbols such as "+" and "-" cause 
		drawingDir = drawingDir+angle;
	}
	
	if(symbol == "-") 							//such like the + similarly each line is an output as an object.
	{
		drawingDir = drawingDir-angle;
	}
	
	if(symbol == "[")							//the current drawing x and y coordinates, the current drawing direction (angle) and the current drawing length are used to generate an object
												//holding this information and that object is pushed onto a stack
	{
		CP = new CurrentPointInfo(x1,x2,angle,length);
		s.push(CP);
	}
	
	if(symbol == "]")							//"]" is encountered the top object if popped from the stack and the current location, current drawing length and current drawing direction (angle) are restored.
	{
		CurrentPointInfo newCpInfo = s.pop();	//here the top object is removed from the stack.
		x1=newCpInfo.getX();					//the next 4 lines are for restoring x1,x2,angle and the length.
		x2=newCpInfo.getX();
		angle = newCpInfo.getCurrentDrawingDirection();
		length = newCpInfo.getCurrentLength();
	}
	
	if ((symbol == "X") || (symbol == "Y"))			//because X and Y and not valid symbols the programs breaks from here.
	{
		System.out.println("");
	}
}
}


public void printAllLines() {
int i=0;
for(i=0;i<lineCount;i++)
  {
  System.out.println("Line " + i + " (x1,y1)=(" 
                             + lines[i].getX1() + "," 
                             + lines[i].getY1() + ") to" + " (x2,y2)=(" 
                             + lines[i].getX2() + "," 
                             + lines[i].getY2() +  ")");
  }
}


// Draw all the computed line stored as LineInfo objects and saved
// in the lines array.Compute offset x and y values so that 10%
// of the image is reserved for non-drawing (the fractal looks better
// if there is some white space around it). Parameters lowerx,lowerY,upperX,upperY
// with values (0,0,1023,1023) most likely
// denote the canvas size (and are using in the saling of the line
// coordinate points before drawing)
public void drawAllLines(Canvas canvas,int lowerX,int lowerY,int upperX,int upperY) {
double xOffset=0.1*upperX;
double yOffset=0.1*upperY;
lowerX+=xOffset;
lowerY+=yOffset;
upperX-=xOffset;
upperY-=yOffset;
int intX1,intX2;
int intY1,intY2;
// Information for scaling: width/height of image
// and width/height of computed coordinates for lines objects
double xFractalLimit=(Xmax-Xmin);
double yFractalLimit=(Ymax-Ymin);
double xImageLimit=upperX-lowerX;
double yImageLimit=upperY-lowerY;
double xRatio=(xImageLimit/xFractalLimit); 
double yRatio=(yImageLimit/yFractalLimit); 

for(int i=0;i<lineCount;i++) 
    {
    // Substract maximum X and Y lines coordinates, scale
    // and then add back in the maximum image coordinates (upperX and upperY)
    intX1=(int) (upperX+xRatio*(lines[i].getX1()-Xmax));
    intY1=(int) (upperY+yRatio*(lines[i].getY1()-Ymax));
    intX2=(int) (upperX+xRatio*(lines[i].getX2()-Xmax));
    intY2=(int) (upperY+yRatio*(lines[i].getY2()-Ymax));
    // draw red (255,0,0) line on canvas
    canvas.drawLine(intX1,intY1,intX2,intY2,255,0,0);
    }
canvas.show();
}

// Keep track of the minimum and maximum x and y values (for scaling purposes)
public void UpdateXminYminXmaxYmax(Double x1,Double y1,Double x2,Double y2) {
if(x1 < Xmin) Xmin=x1;
if(x2 < Xmin) Xmin=x2;
if(x1 > Xmax) Xmax=x1;
if(x2 > Xmax) Xmax=x2;
if(y1 < Ymin) Ymin=y1;
if(y2 < Ymin) Ymin=y2;
if(y1 > Ymax) Ymax=y1;
if(y2 > Ymax) Ymax=y2;
}

// Double the size of the lines array of type LineInfo
public void expandCapacity() {
LineInfo newLines[]= new LineInfo[lines.length*2];
for (int i=0;i < lines.length;i++) {
  newLines[i] = lines[i];
  }
lines=newLines;
}

} // DrawFractal
