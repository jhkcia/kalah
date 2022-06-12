describe('Board', () => {

  // TODO spliit to smaller tests
  // TODO add commands for initializing game
  it('should show turn correctly', async () => {
    cy.visit('http://localhost:3000');

    cy.get('.topnav__link').contains('Login').click();

    cy.get('[data-testid="usernameInput"]')
      .type('User1');

    cy.get('[data-testid="usernameButton"]')
      .click();

    cy.get('.topnav__link').contains('My Board').click();

    cy.get('button').contains('Add Board').click();

    cy.get('.topnav__link').contains('Logout').click();

    cy.get('.topnav__link').contains('Login').click();

    cy.get('[data-testid="usernameInput"]')
      .type('User2');
    cy.get('[data-testid="usernameButton"]')
      .click();

    cy.get('.topnav__link').contains('Available Board').click();

    cy.get('td').last().click() 
    cy.contains('User1\'s Turn');

  });
});