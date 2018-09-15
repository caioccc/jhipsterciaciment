import { browser, ExpectedConditions as ec } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ItemComponentsPage, ItemDeleteDialog, ItemUpdatePage } from './item.page-object';

describe('Item e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let itemUpdatePage: ItemUpdatePage;
    let itemComponentsPage: ItemComponentsPage;
    let itemDeleteDialog: ItemDeleteDialog;

    beforeAll(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Items', async () => {
        await navBarPage.goToEntity('item');
        itemComponentsPage = new ItemComponentsPage();
        expect(await itemComponentsPage.getTitle()).toMatch(/Items/);
    });

    it('should load create Item page', async () => {
        await itemComponentsPage.clickOnCreateButton();
        itemUpdatePage = new ItemUpdatePage();
        expect(await itemUpdatePage.getPageTitle()).toMatch(/Create or edit a Item/);
        await itemUpdatePage.cancel();
    });

    it('should create and save Items', async () => {
        await itemComponentsPage.clickOnCreateButton();
        await itemUpdatePage.setQuantidadeInput('quantidade');
        expect(await itemUpdatePage.getQuantidadeInput()).toMatch('quantidade');
        await itemUpdatePage.setValorItemInput('5');
        expect(await itemUpdatePage.getValorItemInput()).toMatch('5');
        await itemUpdatePage.pedidoSelectLastOption();
        await itemUpdatePage.produtoSelectLastOption();
        await itemUpdatePage.save();
        expect(await itemUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    it('should delete last Item', async () => {
        const nbButtonsBeforeDelete = await itemComponentsPage.countDeleteButtons();
        await itemComponentsPage.clickOnLastDeleteButton();

        itemDeleteDialog = new ItemDeleteDialog();
        expect(await itemDeleteDialog.getDialogTitle()).toMatch(/Are you sure you want to delete this Item?/);
        await itemDeleteDialog.clickOnConfirmButton();

        expect(await itemComponentsPage.countDeleteButtons()).toBe(nbButtonsBeforeDelete - 1);
    });

    afterAll(async () => {
        await navBarPage.autoSignOut();
    });
});
