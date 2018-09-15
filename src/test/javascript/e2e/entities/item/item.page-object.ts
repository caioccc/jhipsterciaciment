import { element, by, ElementFinder } from 'protractor';

export class ItemComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-item div table .btn-danger'));
    title = element.all(by.css('jhi-item div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getText();
    }
}

export class ItemUpdatePage {
    pageTitle = element(by.id('jhi-item-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    quantidadeInput = element(by.id('field_quantidade'));
    valorItemInput = element(by.id('field_valorItem'));
    pedidoSelect = element(by.id('field_pedido'));
    produtoSelect = element(by.id('field_produto'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setQuantidadeInput(quantidade) {
        await this.quantidadeInput.sendKeys(quantidade);
    }

    async getQuantidadeInput() {
        return this.quantidadeInput.getAttribute('value');
    }

    async setValorItemInput(valorItem) {
        await this.valorItemInput.sendKeys(valorItem);
    }

    async getValorItemInput() {
        return this.valorItemInput.getAttribute('value');
    }

    async pedidoSelectLastOption() {
        await this.pedidoSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async pedidoSelectOption(option) {
        await this.pedidoSelect.sendKeys(option);
    }

    getPedidoSelect(): ElementFinder {
        return this.pedidoSelect;
    }

    async getPedidoSelectedOption() {
        return this.pedidoSelect.element(by.css('option:checked')).getText();
    }

    async produtoSelectLastOption() {
        await this.produtoSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async produtoSelectOption(option) {
        await this.produtoSelect.sendKeys(option);
    }

    getProdutoSelect(): ElementFinder {
        return this.produtoSelect;
    }

    async getProdutoSelectedOption() {
        return this.produtoSelect.element(by.css('option:checked')).getText();
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class ItemDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-item-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-item'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
