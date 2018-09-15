import { element, by, ElementFinder } from 'protractor';

export class SaidaComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-saida div table .btn-danger'));
    title = element.all(by.css('jhi-saida div h2#page-heading span')).first();

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

export class SaidaUpdatePage {
    pageTitle = element(by.id('jhi-saida-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    eminenteInput = element(by.id('field_eminente'));
    razaoInput = element(by.id('field_razao'));
    valorInput = element(by.id('field_valor'));
    formaSelect = element(by.id('field_forma'));
    dataInput = element(by.id('field_data'));
    observacoesInput = element(by.id('field_observacoes'));
    corSelect = element(by.id('field_cor'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setEminenteInput(eminente) {
        await this.eminenteInput.sendKeys(eminente);
    }

    async getEminenteInput() {
        return this.eminenteInput.getAttribute('value');
    }

    async setRazaoInput(razao) {
        await this.razaoInput.sendKeys(razao);
    }

    async getRazaoInput() {
        return this.razaoInput.getAttribute('value');
    }

    async setValorInput(valor) {
        await this.valorInput.sendKeys(valor);
    }

    async getValorInput() {
        return this.valorInput.getAttribute('value');
    }

    async setFormaSelect(forma) {
        await this.formaSelect.sendKeys(forma);
    }

    async getFormaSelect() {
        return this.formaSelect.element(by.css('option:checked')).getText();
    }

    async formaSelectLastOption() {
        await this.formaSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async setDataInput(data) {
        await this.dataInput.sendKeys(data);
    }

    async getDataInput() {
        return this.dataInput.getAttribute('value');
    }

    async setObservacoesInput(observacoes) {
        await this.observacoesInput.sendKeys(observacoes);
    }

    async getObservacoesInput() {
        return this.observacoesInput.getAttribute('value');
    }

    async setCorSelect(cor) {
        await this.corSelect.sendKeys(cor);
    }

    async getCorSelect() {
        return this.corSelect.element(by.css('option:checked')).getText();
    }

    async corSelectLastOption() {
        await this.corSelect
            .all(by.tagName('option'))
            .last()
            .click();
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

export class SaidaDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-saida-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-saida'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
