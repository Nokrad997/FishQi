import HomeCard from "../../components/homeCard/HomeCard";
export function openTab(title: string) {
    const tabs = document.querySelectorAll('.homeCards > div');
    tabs.forEach(tab => {

        const htmlTab = tab as HTMLElement;

        if (htmlTab.classList.contains(title)) {
            htmlTab.classList.add('active');
            htmlTab.style.display = 'block';
        } else {
            htmlTab.classList.remove('active');
            htmlTab.style.display = 'none';
        }
    });
}