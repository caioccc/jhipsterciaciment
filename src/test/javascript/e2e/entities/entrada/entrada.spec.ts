import { browser, ExpectedConditions as ec, protractor } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EntradaComponentsPage, EntradaDeleteDialog, EntradaUpdatePage } from './entrada.page-object';

describe('Entrada e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let entradaUpdatePage: EntradaUpdatePage;
    let entradaComponentsPage: EntradaComponentsPage;
    let entradaDeleteDialog: EntradaDeleteDialog;

    beforeAll(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Entradas', async () => {
        await navBarPage.goToEntity('entrada');
        entradaComponentsPage = new EntradaComponentsPage();
        expect(await entradaComponentsPage.getTitle()).toMatch(/Entradas/);
    });

    it('should load create Entrada page', async () => {
        await entradaComponentsPage.clickOnCreateButton();
        entradaUpdatePage = new EntradaUpdatePage();
        expect(await entradaUpdatePage.getPageTitle()).toMatch(/Create or edit a Entrada/);
        await entradaUpdatePage.cancel();
    });

    it('should create and save Entradas', async () => {
        await entradaComponentsPage.clickOnCreateButton();
        await entradaUpdatePage.setClienteInput('cliente');
        expect(await entradaUpdatePage.getClienteInput()).toMatch('cliente');
        await entradaUpdatePage.setValorTotalInput('5');
        expect(await entradaUpdatePage.getValorTotalInput()).toMatch('5');
        await entradaUpdatePage.setValorPagoInput('5');
        expect(await entradaUpdatePage.getValorPagoInput()).toMatch('5');
        await entradaUpdatePage.setDataInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
        expect(await entradaUpdatePage.getDataInput()).toContain('2001-01-01T02:30');
        await entradaUpdatePage.formaPagamentoSelectLastOption();
        await entradaUpdatePage.corSelectLastOption();
        await entradaUpdatePage.save();
        expect(await entradaUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    it('should delete last Entrada', async () => {
        const nbButtonsBeforeDelete = await entradaComponentsPage.countDeleteButtons();
        await entradaComponentsPage.clickOnLastDeleteButton();

        entradaDeleteDialog = new EntradaDeleteDialog();
        expect(await entradaDeleteDialog.getDialogTitle()).toMatch(/Are you sure you want to delete this Entrada?/);
        await entradaDeleteDialog.clickOnConfirmButton();

        expect(await entradaComponentsPage.countDeleteButtons()).toBe(nbButtonsBeforeDelete - 1);
    });

    afterAll(async () => {
        await navBarPage.autoSignOut();
    });
});
