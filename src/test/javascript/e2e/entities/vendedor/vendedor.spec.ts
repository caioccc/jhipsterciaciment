import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { VendedorComponentsPage, VendedorDeleteDialog, VendedorUpdatePage } from './vendedor.page-object';

describe('Vendedor e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let vendedorUpdatePage: VendedorUpdatePage;
    let vendedorComponentsPage: VendedorComponentsPage;
    let vendedorDeleteDialog: VendedorDeleteDialog;

    beforeAll(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Vendedors', async () => {
        await navBarPage.goToEntity('vendedor');
        vendedorComponentsPage = new VendedorComponentsPage();
        expect(await vendedorComponentsPage.getTitle()).toMatch(/Vendedors/);
    });

    it('should load create Vendedor page', async () => {
        await vendedorComponentsPage.clickOnCreateButton();
        vendedorUpdatePage = new VendedorUpdatePage();
        expect(await vendedorUpdatePage.getPageTitle()).toMatch(/Create or edit a Vendedor/);
        await vendedorUpdatePage.cancel();
    });

    it('should create and save Vendedors', async () => {
        await vendedorComponentsPage.clickOnCreateButton();
        await vendedorUpdatePage.setNomeInput('nome');
        expect(await vendedorUpdatePage.getNomeInput()).toMatch('nome');
        await vendedorUpdatePage.setEmailInput('email');
        expect(await vendedorUpdatePage.getEmailInput()).toMatch('email');
        await vendedorUpdatePage.setTelefoneInput('telefone');
        expect(await vendedorUpdatePage.getTelefoneInput()).toMatch('telefone');
        // vendedorUpdatePage.clienteSelectLastOption();
        await vendedorUpdatePage.save();
        expect(await vendedorUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    it('should delete last Vendedor', async () => {
        const nbButtonsBeforeDelete = await vendedorComponentsPage.countDeleteButtons();
        await vendedorComponentsPage.clickOnLastDeleteButton();

        vendedorDeleteDialog = new VendedorDeleteDialog();
        expect(await vendedorDeleteDialog.getDialogTitle()).toMatch(/Are you sure you want to delete this Vendedor?/);
        await vendedorDeleteDialog.clickOnConfirmButton();

        expect(await vendedorComponentsPage.countDeleteButtons()).toBe(nbButtonsBeforeDelete - 1);
    });

    afterAll(async () => {
        await navBarPage.autoSignOut();
    });
});
