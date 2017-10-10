.thumb
.syntax unified

.include "gpio_constants.s"     // Register-adresser og konstanter for GPIO

.text
	.global Start
	
Start:

    // Skriv din kode her...
// R0 - R12 is general purpose
// We will store the adress to PORT B DIN to R11
// We will store the adress to PORT E DOUT to R12
// We will store the mask we're using to power the LED at R10
// We will Store the mask we're using to check PB0 at R9

// Useful constants to quickly access:
    LDR R0, =GPIO_BASE	// Load the value of GPIO_BASE into R0 (mem-adress of GPIO)
    LDR R1, =PORT_SIZE	// Load the value of PORT_SIZE into R1 (36)
    LDR R10, =0b00000100	// Mask setting PIN_2 to 1
    LDR R9, =0b001000000000	// Mask for PIN_9 to 1
// Start actual use:

// Calculate the memory adress of GPIO_PORT_E_DOUT
    MOV R2, PORT_E		// Move the value of PORT_E into R2
    // R2 = 4
    MOV R3, GPIO_PORT_DOUT // Move the value of GPIO_PORT_DOUT into R3
    // R3 = 12
    MOV R4, PORT_SIZE	// Move the value of PORT SIZE into R4
    // R4 = 36
    MUL R2, R2, R4		// Multiply the value of Port E with Port Size in Bytes
    // R2 = 4 * 36 = 144
    ADD R3, R2, R3		// Add the value of Port E offset with GPIO_PORT_DOUT offset
    // R3 = 156
    ADD R12, R0, R3		// Add the value of GPIO_BASE and offset for Port E DOUT
    // R12 = 0x40006000 + #156 = 0x40006000 + 0x0000009C = 0x4000609C
    // R12 now has the supposed adress of Port E DOUT

// Calculate the memory adress of GPIO_PORT_B_DIN
    MOV R2, PORT_B		// Move the value of PORT_B into R2
    // R2 = 1
    MOV R3, GPIO_PORT_DIN	// Move the value of GPIO_PORT_DIN into R3
    // R3 = 28
    MUL R2, R2, R1		// Multiplying the value of Port B with Port Size in bytes
    // R2 = 1 * 36 = 36
    ADD R3, R2, R3		// Add the offset of GPIO_PORT_DIN to offset of PORT B
    // R3 = 36 + 28 = 64
    ADD R11, R0, R3		// Add the offset of PORT_B_DIN to GPIO_BASE
    // R11 = 0x40006000 + 64

Loop:

// Check if the value in PORT_B_DIN is equal to mask for PIN_9
	LDR R3, [R11]		// Get the contents of the register of PB9
	AND R2, R9, R3		// Get the bitwise AND of the mask and the values of PB9
	// If the pin is 1 then the value in R2 should be the mask
	CMP R2, R9			// If the value of PIN_9 is 1 then CMP R2, R9 --> Z = 1
	BNE Pressed			// if the value was not the same, then we branch to Pressed
	BEQ Notpressed		// Else we branch to Notpressed
	Pressed:
		STR R10, [R12]	// Write the mask to the DOUT register on Port E
		B Loop			// Branch to the top of the loop

	Notpressed:
		LDR R3, =0b0	// Load the value of 0 into R3
		STR R3, [R12]	// Write the value of 0 to the whole DOUT register on Port E

	B Loop				// Branch to the top of the loop, eternal loop

NOP // Behold denne på bunnen av fila

