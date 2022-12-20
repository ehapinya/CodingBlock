package Components;

import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;
import java.awt.*;

public class MainPanel extends JPanel implements ActionListener, ItemListener, MouseListener {

	// position and size of walkway
	int posX = 130;
	int posY = 20;
	int size = 20;

	// position of the 3 vertices for drawing player
	int [] playerX = {130+10-5,130+10+5,130+10-5};
	int [] playerY = {20+10+20*15-5,20+10+20*15,20+10+20*15+5};

	Timer timer = new Timer(1000, this);		// a timer for countdown
	Timer timer_run = new Timer(1000, this);	// a timer for running
	int runf0_count = 0;						// a repeating controller of f0
	int runf1_count = 0;						// a repeating controller of f1
	Boolean run = false;						// a running controller

	// an array of command buttons
	JButton [] arrow = {new JButton("<"),new JButton("^"),new JButton(">"),new JButton("f0"),new JButton("f1")};
	JPanel arrow_panel = new JPanel(); 			// a small panel for containing arrow commands

	// 2 predefined colors
	Color darkred = new Color(225,0,0);
	Color green = new Color(0,175,0);

	// an array of color condition buttons *** implicit casting is done here ***
	JButton [] color = {new Button(darkred),new Button(Color.BLUE),new Button(green)};
	JPanel color_panel = new JPanel(); 			// a small panel for containing color condition buttons

	JLabel command_label = new JLabel(" Command"); 	// a predefined label in command panel
	JPanel command_panel = new JPanel(); 			// a panel for containing label, arrow_panel and color_panel

	JButton execution_button = new JButton("");		// the button that displays what command is being executed now
	String speed = "1X";											// a default running speed
	String [] speed_choice = {"1X","2X","4X","10X"}; 				// an array of running speeds
	JComboBox<String> run_speed = new JComboBox<>(speed_choice);	// a combo box of running speeds
	JButton run_button = new Button("RUN", Color.ORANGE);			// a button for making the program starts running
	JButton break_button = new JButton("Break");					// a button for breaking the program
	JPanel run_panel = new JPanel();	// a panel for containing execution_button, run_button, break_button and run_speed

	// an array of toggle buttons for function f0
	JToggleButton [] f0_arry = {new JToggleButton(),new JToggleButton(),new JToggleButton(),new JToggleButton()};
	String [] f0_move = {"","","",""};	// an predefined commands for toggle buttons of function f0
	Color [] f0_color = {Color.GRAY,Color.GRAY,Color.GRAY,Color.GRAY};	// an array of predefined colors for toggle buttons of function f0
	JLabel f0_label = new JLabel("f0");	// a predefined label in f0 panel
	JPanel f0_panel = new JPanel();		// a panel for containing label and toggle buttons of function f0

	// an array of toggle buttons for function f1
	JToggleButton [] f1_arry = {new JToggleButton(),new JToggleButton(),new JToggleButton(),new JToggleButton()};
	String [] f1_move = {"","","",""};	// a predefined label in f1 panel
	Color [] f1_color = {Color.GRAY,Color.GRAY,Color.GRAY,Color.GRAY};	// an array of predefined colors for toggle buttons of function f1
	JLabel f1_label = new JLabel("f1");	// a panel for containing label and toggle buttons of function f1
	JPanel f1_panel = new JPanel();		// a predefined label in f1 panel

	// an array of toggles buttons for function f0 and f1
	JToggleButton [] f0f1 = {f0_arry[0],f0_arry[1],f0_arry[2],f0_arry[3],f1_arry[0],f1_arry[1],f1_arry[2],f1_arry[3]};

	// a button for clearing all commands and conditions toggle buttons of function f0 and f1
	JButton clear_button = new JButton("Clear all functions");
	JLabel function_label = new JLabel("Function");		// a predefined label in function panel
	JPanel function_panel = new JPanel();				// a panel for containing label, f0_panel, f1_panel and clear_button

	// an instruction area
	JTextArea instruction_label = new JTextArea("\nInstruction:\n\nYou have to get all coins by using the given commands, \n\n< : turn left \n^ : move forward \n> : turn right \nf0 : repeat function f0 again \n   (since it will automatically\n    call"
			+ " f0)\nf1 : call function f1 \nColor Button : \n   make a color condition\n\n       This is a challenge\nLet's finish this in 2 hours!!!");
	int count = 0;		// a time counter
	int h = 2;			// a predefined number of hours
	int m = 0;			// a predefined number of minutes
	int s = 0;			// a predefined number of seconds
	JLabel count_label = new JLabel("You have "+h+"h "+m+"m "+s+"s left");	// a label displaying the remaining time
	JPanel information_panel = new JPanel();	// a panel for containing instruction_label and count_label

	JPanel action_panel = new JPanel();	// a big panel for containing command_panel, function_panel and information_panel
	JPanel controlpanel = new JPanel(); // a half-page panel for containing run_panel and action_panel

	static String moveCon = "";			// a predefined command for command being executed
	static Color colorCon = Color.GRAY;	// a predefined color condition for command being executed
	static String direction = "e";				// a predefined direction of player

	Color [] newColor = new Color[17*16*2];	// a blank array with predefined size for clearing colorCoin
	Color [] colorCoin = newColor;			// an array for collecting the base color of that position and using it for covering a coin
	int [] newXCoin = new int[17*16*2];		// a blank array with predefined size for clearing posXCoin
	int [] newYCoin = new int[17*16*2];		// a blank array with predefined size for clearing posYCoin
	int [] posXCoin = newXCoin;				// an array for collecting the x-axis value of that position and using it for covering a coin
	int [] posYCoin = newYCoin;				// an array for collecting the y-axis value of that position and using it for covering a coin
	int countCoin = -1;						// a step counter
	// I have 17*16 blocks of walkway but I set those size as 17*16*2
	// to reserve for the people that can command code to collect all coin but have repeated walking the same position

    public MainPanel() {

    	JFrame frame = new JFrame("Coding Block");				// create an object of JFrame with specified title
        frame.setSize(600,760);									// set a frame's size
        frame.setVisible(true);									// set visible
        frame.setLocationRelativeTo(null);						// set a location at center the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// set default Close Operation

        frame.setLayout(new GridLayout(2,1));					// set a frame's layout
        frame.add(this);										// add MainPanel into the first half of frame
        frame.add(controlpanel);								// add controlpanel into the second half of frame
    	
        controlpanel.setLayout(new BorderLayout());				// set controlpanel's layout

    	controlpanel.add(run_panel, BorderLayout.NORTH);		// add run_panel into the north part of controlpanel
    	run_panel.setLayout(new GridLayout(1,4));				// set run_panel's layout
    	run_panel.add(execution_button);						// add execution_button into run_panel
    	run_panel.add(run_button);								// add run_button into run_panel
    	run_panel.add(break_button);							// add break_button into run_panel
    	run_panel.add(run_speed);								// add run_speed into run_panel
   
    	controlpanel.add(action_panel, BorderLayout.CENTER);	// add action_panel into the center part of controlpanel
    	action_panel.setLayout(new GridLayout(1,3,10,10));		// set a action_panel's layout
    	action_panel.add(command_panel);						// add command_panel into action_panel
    	action_panel.add(function_panel);						// add function_panel into action_panel
    	action_panel.add(information_panel);					// add information_panel into action_panel
    	
    	arrow_panel.setLayout(new GridLayout(2,3));				// set arrow_panel's layout
    	for (int i=0; i<5; i++) {								
			arrow_panel.add(arrow[i]);							// add all elements in arrow array into arrow_panel
		}
    	
    	color_panel.setLayout(new GridLayout(3,1));				// set color_panel's layout
		for (int i=0; i<3; i++) {
			color_panel.add(color[i]);							// add all elements in color array into arrow_panel
		}
    	
    	command_panel.setLayout(new BorderLayout());			// set command_panel's layout
    	command_panel.add(command_label, BorderLayout.NORTH);	// add command_label into the north part of command_panel
    	command_panel.add(arrow_panel, BorderLayout.CENTER);	// add arrow_panel into the center part of command_panel
    	command_panel.add(color_panel, BorderLayout.EAST);		// add color_panel into the east part of command_panel
    	
    	function_panel.setLayout(new GridLayout(5,1,10,10));	// set function_panel's layout with a specified gap between each components
    	function_panel.add(function_label);						// add function_label into function_panel
    	function_panel.add(f0_panel);							// add f0_panel into function_panel
    	function_panel.add(f1_panel);							// add f1_panel into function_panel
    	function_panel.add(clear_button);						// add clear_button into function_panel
    	
    	// create an object of JTextArea to display a hint for user
    	JTextArea hint = new JTextArea("     Please click on each box \n     of function before \n     choosing the command");
    	hint.setEditable(false);								// prevent the user from entering text into the wrong text area
		hint.setOpaque(false);									// make text area transparent
		hint.setLineWrap(true);									// set the line to be wrapped if they are too long to fit within the allocated width
		hint.setWrapStyleWord(true);							// set the line to be wrapped at word whitespace if they are too long to fit within the allocated width
    	function_panel.add(hint);								// add hint into function_panel
    	
    	f0_panel.setLayout(new GridLayout(1,5));				// set f0_panel's layout
    	f0_panel.add(f0_label);									// add f0_label into f0_panel
		for (int i=0; i<4; i++) {
			f0_panel.add(f0_arry[i]);							// add all elements in f0_arry array into f0_panel
		}
    	f1_panel.setLayout(new GridLayout(1,5));				// set f1_panel's layout
    	f1_panel.add(f1_label);									// add f1_label into f1_panel
		for (int i=0; i<4; i++) {
			f1_panel.add(f1_arry[i]);							// add all elements in f1_arry into f1_panel
		}
    	
    	information_panel.setLayout(new BorderLayout());		// set information_panel's layout
    	JPanel p1 = new JPanel();								// create an object of JPanel
    	p1.setLayout(new GridLayout(2,1));						// set p1's layout
    	p1.add(count_label);									// add count_label into p1
    	p1.add(new JLabel("   "));								// add blank label into p1 to make count_label not too close to the edge of frame
    	information_panel.add(instruction_label, BorderLayout.CENTER);	// add instruction_label into the center part of information_panel
    	information_panel.add(p1, BorderLayout.SOUTH);			// add p1 into information_label
    	
		instruction_label.setEditable(false);					// prevent the user from entering text into the wrong text area
		instruction_label.setOpaque(false);						// make text area transparent
		instruction_label.setLineWrap(true);					// set the line to be wrapped if they are too long to fit within the allocated width
		instruction_label.setWrapStyleWord(true);				// set the line to be wrapped at word whitespace if they are too long to fit within the allocated width

		for (int i=0; i<4; i++) {
    		f0_arry[i].addItemListener(this);					// make all elements in f0_arry array can fire an ItemEvent in this class
    	}

		for (int i=0; i<4; i++) {
			f1_arry[i].addItemListener(this);					// make all elements in f1_arry array can fire an ItemEvent in this class
		}

    	for (int i=0; i<5; i++) {
    		arrow[i].addActionListener(this);					// make all elements in arrow array can fire an ActionEvent in this class
    	}
    	
    	for (int i=0; i<3; i++) {
    		color[i].addActionListener(this);					// make all elements in color array can fire an ActionEvent in this class
    	}
    	
    	run_button.addActionListener(this);						// make run_button can fire an ActionEvent in this class
    	break_button.addActionListener(this);					// make break_button can fire an ActionEvent in this class
    	run_speed.addActionListener(this);						// make run_speed can fire an ActionEvent in this class

    	clear_button.addActionListener(this);					// make clear_button can fire an ActionEvent in this class
    	
    	this.addMouseListener(this);							// make this can fire an MouseEvent in this class
    	action_panel.addMouseListener(this);					// make action_panel can fire an MouseEvent in this class
    	run_panel.addMouseListener(this);						// make run_panel can fire an MouseEvent in this class
	}
    
    protected void paintComponent(Graphics g) {
    	
        super.paintComponent(g);	// clear the JPanel
        
        // set colors and fill round rectangles for making walkway
        g.setColor(darkred);
        for (int i=0; i<15; i+=1) {
        	g.fillRoundRect(posX, posY+size*i, size-1, size-1, 5, 5);
        }
        g.setColor(green);
        g.fillRoundRect(posX, posY+size*15, size-1, size-1, 5, 5);
        for (int i=0; i<7; i+=1) {
        	for (int j=0; j<16; j+=1) {
        		g.fillRoundRect(posX+size+size*i, posY+size*j, size-1, size-1, 5, 5);
        	}
        }
        g.setColor(Color.BLUE);
        for (int i=0; i<16; i+=1) {
        	g.fillRoundRect(posX+size*8, posY+size*i, size-1, size-1, 5, 5);
        }
        g.setColor(green);
        for (int i=0; i<7; i+=1) {
        	for (int j=0; j<16; j+=1) {
        		g.fillRoundRect(posX+size*9+size*i, posY+size*j, size-1, size-1, 5, 5);
        	}
        }
        g.setColor(darkred);
        for (int i=0; i<16; i+=1) {
        	g.fillRoundRect(posX+size*16, posY+size*i, size-1, size-1, 5, 5);
        }

        g.setColor(Color.orange);
		for (int i=0; i<17; i+=1) {
			for (int j=0; j<16; j+=1) {
				g.fillOval(posX+size*i+5, posY+size*j+5, 10, 10);
			}
		}
		g.setColor(green);
		g.fillRect(135,325,10,10);
		
		// set colors and fill rectangles for covering a coin
		for (int i=0; i<=countCoin; i++) {
			g.setColor(colorCoin[i]);
			g.fillRect(posXCoin[i],posYCoin[i],10,10);
		}
		
		g.setColor(Color.BLACK);	// set color
		move(g);					// draw player according to condition
    }
    
    public void actionPerformed(ActionEvent e) {

		if (e.getSource()==timer) {				// if the source of event is timer, running this following statement
    		count++;							// increase the value of count, second by 1
    		s=60*60*2-count;					// s equals total time, 2 hours minus the elapsed time
    		if (s>=3600) {						// if time remaining is greater than or equal to 3600 seconds, 1 hour
    			// calculate hours remaining by dividing s by 60*60 and use math.floor for rounding value down to the nearest integer
    			// from Math.floor return double, so I use explicit casting because I want the value of hours in form of integer 
    			h=(int) Math.floor(s/60/60);	
    			s-=h*60*60;						// decrease the value of s by h*60*60
    			// calculate minutes remaining by dividing s by 60 and use math.floor for rounding value down to the nearest integer
    			// from Math.floor return double, so I use explicit casting because I want the value of minutes in form of integer 
    			m=(int) Math.floor(s/60);		
    			s-=m*60;						// decrease the value of s by m*60
    			count_label.setText("You have "+h+"h "+m+"m "+s+"s left");	// set the text of count_label to display time remaining
    		}
    		else if (s==0){								// if time remaining is 0
    			count_label.setText("Time's up");		// set the text of count_label to display "Time's up"
    			super.paintComponent(getGraphics());	// clear panel
    			timer.removeActionListener(this);		// remove registration between timer and this class
    			timer_run.removeActionListener(this);	// remove registration between timer_run and this class
    			// so user can not play this game after time's up
    		}
    		else {								// if time remaining less than 1 hour and not 0
    			h=0;							// assign 0 to h
    			// calculate minutes remaining by dividing s by 60 and use math.floor for rounding value down to the nearest integer
    			// from Math.floor return double, so I use explicit casting because I want the value of minutes in form of integer 
    			m=(int) Math.floor(s/60);
    			s-=m*60;						// decrease the value of s by m*60
    			count_label.setText("You have "+h+"h "+m+"m "+s+"s left");	// set the text of count_label to display time remaining
    		}
    	}
		
		else if (e.getSource()==run_button) {	// if the source of event is run_button, running this following statement
    		runf0_count = 0;					// assign 0 to runf0_count
    		runf1_count = 0;					// assign 0 to runf1_count
    		if (speed=="2X") {					// if user have set speed to be 2X
    			timer_run.setDelay(1000/2);		// set delay of timer_run to be 1000/2 ms or 0.5 seconds
			}
    		else if (speed=="4X") {				// if user have set speed to be 4X
    			timer_run.setDelay(1000/4);		// set delay of timer_run to be 1000/4 ms or 0.25 seconds
			}
    		else if (speed=="10X") {			// if user have set speed to be 10X
    			timer_run.setDelay(1000/10);	// set delay of timer_run to be 1000/10 ms or 0.1 seconds
			}
    		else								// if user did not set speed or set it to be 1X
				timer_run.setDelay(1000);		// set delay of timer_run to be 1000 ms or 1 second
    		// set position and direction of player, reset position, counter and color for covering coin to be the same starting point
    		playerX[0] = 130+10-5;
			playerX[1] = 130+10+5;
			playerX[2] = 130+10-5;
			playerY[0] = 20+10+20*15-5;
			playerY[1] = 20+10+20*15;
			playerY[2] = 20+10+20*15+5;
			direction = "e";
			posXCoin = newXCoin;
			posYCoin = newYCoin;
			colorCoin = newColor;
			countCoin = -1;
			// start timer_run
    		timer_run.start();
    	}

		else if (e.getSource()==timer_run) {				// if the source of event is timer_run, running this following statement
			if (runf1_count>=1 && runf1_count<5) {			// check the condition
				run = true;									// set run to be true
				moveCon = f1_move[runf1_count-1];			// set executing command to be the same the (runf1_count)th element of f1_move
				setcolorCon((f1_color[runf1_count-1]));		// set colorCon to be the same the (runf1_count)th element of f1_move
				execution_button.setText(moveCon);			// set text of execution_button to be moveCon
				if (colorCon!=Color.gray) {					// if colorCon is not gray
					execution_button.setBackground(colorCon);	// set background of execution_button to be colorCon
					execution_button.setOpaque(true);		// make excution_button's background color transparent
				}
				else										// if colorCon is gray
					execution_button.setOpaque(false);		// paint excution_button's background color
				runf1_count++;								// increase the value of runf1_count by 1
				repaint();									// tell JVM to call paintComponent method
			}
			else if (runf0_count<4) {						// check the condition
				run = true;									// set run to be true
				moveCon = f0_move[runf0_count];				// set executing command to be the same the (runf0_count)th element of f0_move
				setcolorCon(f0_color[runf0_count]);			// set colorCon to be the same the (runf0_count)th element of f0_move
				execution_button.setText(moveCon);			// set text of execution_button to be moveCon
				if (colorCon!=Color.gray) {					// if colorCon is not gray
					execution_button.setBackground(colorCon);	// set background of execution_button to be colorCon
					execution_button.setOpaque(true);		// make excution_button's background color transparent
				}
				else										// if colorCon is gray
					execution_button.setOpaque(false);		// paint excution_button's background color
				runf0_count++;								// increase the value of runf0_count by 1
				repaint();									// tell JVM to call paintComponent method
			}
			else {											// if nothing meets condition
				run = false;								// set run to be false
				timer_run.stop();							// stop timer_run
			}
		}
		
		else if (e.getSource()==break_button) {				// if the source of event is break_button,
			timer_run.stop();								// stop timer_run
		}

		else if (e.getSource()==run_speed) {				// if the source of event is run_speed,
			speed = run_speed.getSelectedItem().toString();	// assign string of the selected item to speed
		}
		
    	else if (e.getSource()==clear_button) {				// if the source of event is clear_button, running this following statement
    		for (int i = 0; i<4; i++) {						// set all variables about f0 and f1 to the same be as default values
    			f0_move[i] = "";
    			f0_color[i] = Color.gray;
    			f0_arry[i].setText(f0_move[i]);
    			f0_arry[i].setBackground(f0_color[i]);
    			f0_arry[i].setOpaque(false);
				f1_move[i] = "";
				f1_color[i] = Color.gray;
				f1_arry[i].setText(f0_move[i]);
				f1_arry[i].setBackground(f0_color[i]);
				f1_arry[i].setOpaque(false);
			}
		}
    	
    	else if (Arrays.asList(arrow).contains(e.getSource())) {	// if the source of event is in arrow array, running this following statement
    		for (int i=0; i<5; i++) {
				if (e.getSource() == arrow[i]) {					// if arrow[i] is the source of event
					for (int j = 0; j < 4; j++) {
						if (f0_arry[j].isSelected()) {						// if j th element of f0_arry array is selected
							if (f0_move[j]==e.getActionCommand()) {			// if move condition of that element is already the same as action command
								f0_move[j] = "";							// assign blank string to move condition of that element
								f0_arry[j].setText("");						// set text of that element to be blank string
							}
							else {											// if move condition of that element is not the same as action command
								f0_move[j] = e.getActionCommand();			// assign action command to move condition of that element
								f0_arry[j].setText(e.getActionCommand());	// set text of that element to be the same as action command
							}
						}
					}
					for (int j = 0; j < 4; j++) {
						if (f1_arry[j].isSelected()) {						// if j th element of f1_arry array is selected
							if (f1_move[j]==e.getActionCommand()) {			// if move condition of that element is already the same as action command
								f1_move[j] = "";							// assign blank string to move condition of that element
								f1_arry[j].setText("");						// set text of that element to be blank string
							}
							else {											// if move condition of that element is not the same as action command
								f1_move[j] = e.getActionCommand();			// assign action command to move condition of that element
								f1_arry[j].setText(e.getActionCommand());	// set text of that element to be the same as action command
							}
						}
					}
				}
			}
    	}
    	
    	else if (Arrays.asList(color).contains(e.getSource())) {	// if the source of event is in color array, running this following statement
    		for (int i=0; i<3; i++) {
    			if (e.getSource()==color[i]) {						// if color[i] is the source of event
					for (int j=0; j<4; j++) {
						if (f0_arry[j].isSelected()) {								// if j th element of f0_arry array is selected
							if (f0_color[j]==((Button) e.getSource()).getColor()) {	// if color condition of that element is already the same as the color of action command
								// I have created objects in color array by using Button, a new class with getColor method as created type
								// but type of array, declared type is JButton. so I have to do an explicit casting for using getColor method
								f0_arry[j].setBackground(Color.gray);				// set background color of that element to be gray
								f0_color[j]=Color.gray;								// assign color gray to color condition of that element
								f0_arry[j].setOpaque(false);						// make f0_arry[j]'s background color transparent
							}
							else {
								f0_arry[j].setBackground(((Button) e.getSource()).getColor());	// if color condition of that element is already the same as the color of action command
								// I have created objects in color array by using Button, a new class with getColor method as created type
								// but type of array, declared type is JButton. so I have to do an explicit casting for using getColor method
								f0_color[j] = ((Button) e.getSource()).getColor();				// assign color of source of event to color condition of that element
								f0_arry[j].setOpaque(true);										// paint f0_arry[j]'s background color
							}
						}
					}
					for (int j=0; j<4; j++) {
						if (f1_arry[j].isSelected()) {								// if j th element of f1_arry array is selected
							if (f1_color[j]==((Button) e.getSource()).getColor()) {	// if color condition of that element is already the same as the color of action command
								// I have created objects in color array by using Button, a new class with getColor method as created type
								// but type of array, declared type is JButton. so I have to do an explicit casting for using getColor method
								f1_arry[j].setBackground(Color.gray);				// set background color of that element to be gray
								f1_color[j]=Color.gray;								// assign color gray to color condition of that element
								f1_arry[j].setOpaque(false);						// make f1_arry[j]'s background color transparent
							}
							else {
								f1_arry[j].setBackground(((Button) e.getSource()).getColor());	// if color condition of that element is already the same as the color of action command
								// I have created objects in color array by using Button, a new class with getColor method as created type
								// but type of array, declared type is JButton. so I have to do an explicit casting for using getColor method
								f1_color[j] = ((Button) e.getSource()).getColor();				// assign color of source of event to color condition of that element
								f1_arry[j].setOpaque(true);										// paint f1_arry[j]'s background color
							}
						}
					}
    			}
    		}
    	}
    }
    
    public void itemStateChanged(ItemEvent e) {
    	
    	for (int i=0; i<8; i++) {
    		if (e.getSource()==f0f1[i]) {			// if f0f1[i] is the source of event, set selected of other elements in f0f1 to be false
    			for (int j=0; j<i; j++) {
    				f0f1[j].setSelected(false);
    			}
    			for (int j=i+1; j<8; j++) {
    				f0f1[j].setSelected(false);
    			}
    		}
    	}
    }

	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {
		timer.start();								// if mouse enter the panel of this class, action_panel and run_panel, starting timer
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	protected void move(Graphics g) {		// this method is used for moving player

		if (playerX[1]>=130 && playerX[1]<=470 && playerY[1]>=20 && playerY[1]<=340) {	// if position of player is in walkway, running this following statement
			
			if (((colorCon==Color.GRAY) || (colorCon==posColor(playerX[1]))) && run==true ) {	// if run is true and color condition is gray or the same with walkway's color
				
				if (moveCon=="^") {						// if move condition is "^", check the direction and then move forward in that direction
					
					if (direction=="e") {
						for (int i = 0; i<3; i++) {
							playerX[i]+=size;
						}
					}
					else if (direction=="w") {
						for (int i = 0; i<3; i++) {
							playerX[i]-=size;
						}
					}
					else if (direction=="n") {
						for (int i = 0; i<3; i++) {
							playerY[i]-=size;
						}
					}
					else if (direction=="s") {
						for (int i = 0; i<3; i++) {
							playerY[i]+=size;
						}
					}

					if (posColor(playerX[1])!=null) {	// if there are walkway at that position
						countCoin++;					// increase countCoin by 1, and check the direction and then assign position and color for covering the coin
						if (direction=="n") {
							posXCoin[countCoin] = playerX[1]-5;
							posYCoin[countCoin] = playerY[1]+5-5;
						}
						else if (direction=="e") {
							posXCoin[countCoin] = playerX[1]-5-5;
							posYCoin[countCoin] = playerY[1]-5;
						}
						else if (direction=="w") {
							posXCoin[countCoin] = playerX[1]+5-5;
							posYCoin[countCoin] = playerY[1]-5;
						}
						else {
							posXCoin[countCoin] = playerX[1]-5;
							posYCoin[countCoin] = playerY[1]-5-5;
						}
						colorCoin[countCoin] = posColor(playerX[1]);
					}


				}
				else if (moveCon=="<") {				// if move condition is "<", check the direction and then turn left

					if (direction=="e") {
						playerX[1]-=5;
						playerX[2]+=10;
						playerY[0]+=10;
						playerY[1]-=5;
						direction = "n";
					}
					else if (direction=="n") {
						playerX[1]-=5;
						playerX[0]+=10;
						playerY[2]-=10;
						playerY[1]+=5;
						direction="w";
					}
					else if (direction=="w") {
						playerX[1]+=5;
						playerX[2]-=10;
						playerY[0]-=10;
						playerY[1]+=5;
						direction="s";
					}
					else {
						playerX[1]+=5;
						playerX[0]-=10;
						playerY[2]+=10;
						playerY[1]-=5;
						direction = "e";
					}
				}
				else if (moveCon==">") {				// if move condition is ">", check the direction and then turn right

					if (direction=="e") {
						playerX[0] += 10;
						playerX[1] -= 5;
						playerY[1] += 5;
						playerY[2] -= 10;
						direction="s";
					}
					else if (direction=="n") {
						playerX[2] -= 10;
						playerX[1] += 5;
						playerY[1] += 5;
						playerY[0] -= 10;
						direction="e";
					}
					else if (direction=="w") {
						playerX[0] -= 10;
						playerX[1] += 5;
						playerY[1] -= 5;
						playerY[2] += 10;
						direction="n";
					}
					else {
						playerX[2] += 10;
						playerX[1] -= 5;
						playerY[1] -= 5;
						playerY[0] += 10;
						direction = "w";
					}
				}
				else if (moveCon=="f0") {				// if move condition is "f0", set runf0_count to be 0
					runf0_count = 0;
				}
				else if (moveCon=="f1") {				// if move condition is "f1", set runf1_count to be 1
					runf1_count = 1;
				}
			}
		}
		else						// if position of player is not in walkway, stopping timer_run
			timer_run.stop();


		g.fillPolygon(playerX, playerY, 3);				// draw player
		run = false;									// set run to be false
	}

	Color posColor(int x) {						// this method is used for getting the walkway's color at specified position
		
		if (playerY[1]<20 || playerY[1]>350) {
			return null;
		}
		else if (x>130 && x<150 && playerY[1]>320 && playerY[1]<340) {
			return green;
		}
		else if (x>130 && x<150) {
			return darkred;
		}
		else if (x>450 && x<470) {
			return darkred;
		}
		else if (x>290 && x<310) {
			return Color.BLUE;
		}
		else if (x<130) {
			return null;
		}
		else if (x>470) {
			return null;
		}
		else if (x>150 && x<450)
			return green;
		else
			return null;
	}

	public void setcolorCon(Color color) {		// this method is used for setting the colorCon to be the same as specified color
		colorCon =color;
	}
}
