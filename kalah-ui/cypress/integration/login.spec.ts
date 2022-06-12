describe('Login', () => {
  it('should login', () => {
    cy.visit('http://localhost:3000');

    cy.get('.topnav__link').contains('Login').click();


    cy.get('[data-testid="usernameInput"]')
      .type('God Father');

    cy.get('[data-testid="usernameButton"]')
      .click();

    cy.contains('Welcome God Father');
  });
});